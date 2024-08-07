package com.shop.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shop.command.ProductAddCommand;
import com.shop.command.ProductEditCommand;
import com.shop.dto.*;
import com.shop.model.Category;
import com.shop.model.Product;
import com.shop.model.ProductAbout;
import com.shop.model.ProductSize;
import com.shop.repository.ProductAboutRepository;
import com.shop.repository.ProductRepository;
import com.shop.repository.ProductSizeRepository;
import com.shop.shared.Exceptions.BadRequestException;
import com.shop.shared.classes.BaseService;
import com.shop.shared.classes.Response;
import com.shop.shared.enums.ErrorMessagesEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ProductService extends BaseService {

    private final ObjectMapper objectMapper;
    private final ProductRepository productRepository;
    private final ProductSizeRepository sizeRepository;
    private final ProductAboutRepository aboutRepository;
    private final UserProductSearchService userProductSearchService;

    public ResponseEntity<Response> getAll(Long categoryId, Byte sort) {
        List<Product> products;
        switch (Optional.ofNullable(sort).orElse((byte) 0)) {
            case 1:
                products = productRepository.getAllExpensive(categoryId);
                break;
            case 2:
                products = productRepository.getAllCheap(categoryId);
                break;
            case 3:
                products = productRepository.getAllMostBuy(categoryId);
                break;
            default:
                products = productRepository.getAll(categoryId);
                break;
        }
        List<ProductDto> dto = new ArrayList<>();
        for (Product product : products) {
            ProductDto productDto = new ProductDto();
//                if (product.getOffPercent() > 0) {
//                    product.setPrice(product.getPrice() - (product.getPrice() * product.getOffPercent() / 100));
//                }
            productDto.setProduct(product);
            Optional<List<ProductSize>> productSizes = sizeRepository.findByProductId(product.getProductId());
            Optional<List<ProductAbout>> abouts = aboutRepository.findByProductId(product.getProductId());
            productDto.setProductSize(productSizes.orElse(Collections.emptyList()));
            productDto.setProductAbout(abouts.orElse(Collections.emptyList()));
            dto.add(productDto);
        }
        return successResponse(new ProductListDto(dto));
    }

    public ResponseEntity<Response> retrieve(Long productId) {
        Optional<ProductRetrieve> product = productRepository.retrieve(productId);
        if (product.isPresent()) {
            ProductRetrieveDto dto = new ProductRetrieveDto();
            dto.setProduct(product.get());
            Optional<List<ProductSize>> sizes = sizeRepository.findByProductId(productId);
            Optional<List<ProductAbout>> abouts = aboutRepository.findByProductId(productId);
            dto.setProductSize(sizes.orElse(Collections.emptyList()));
            dto.setProductAbout(abouts.orElse(Collections.emptyList()));
            return successResponse(new ProductRetrieveFinalDto(dto));
        } else {
            throw new BadRequestException(ErrorMessagesEnum.NO_PRODUCTS_FOUND.getMessage());
        }
    }

    public List<ProductDto> listByIds(List<Long> productIds) {
        List<Product> products = new ArrayList<>();

        productIds.forEach(id -> {
            products.add(productRepository.findByProductId(id).get());
        });

        List<ProductDto> dto = new ArrayList<>();

        for (Product product : products) {
            ProductDto productDto = new ProductDto();
            productDto.setProduct(product);
            Optional<List<ProductSize>> productSizes = sizeRepository.findByProductId(product.getProductId());
            Optional<List<ProductAbout>> productAbouts = aboutRepository.findByProductId(product.getProductId());
            productDto.setProductSize(productSizes.orElse(Collections.emptyList()));
            productDto.setProductAbout(productAbouts.orElse(Collections.emptyList()));
            dto.add(productDto);
        }

        return dto;
    }

    public ResponseEntity<Response> edit(ProductEditCommand command) {
        try {
            Optional<Product> product = productRepository.findByProductId(command.getProductId());
            if (product.isPresent()) {
                updateProductDetails(product.get(), command);
                productRepository.save(product.get());
                if (command.getSize() != null) {
                    List<ProductSize> sizes = objectMapper.readValue(command.getSize(), new TypeReference<List<ProductSize>>() {
                    });
                    List<ProductAbout> abouts = objectMapper.readValue(command.getAbout(), new TypeReference<>() {
                    });
                    sizes.forEach(productSize -> productSize.setProductId(command.getProductId()));
                    sizeRepository.saveAll(sizes);
                    abouts.forEach(productAbout -> productAbout.setProductId(command.getProductId()));
                    aboutRepository.saveAll(abouts);
                }
                return successResponse();
            } else {
                throw new BadRequestException(ErrorMessagesEnum.NO_PRODUCTS_FOUND.getMessage());
            }
        } catch (Exception e) {
            return null;
        }
    }

    public ResponseEntity<Response> add(ProductAddCommand command) {
        try {
            List<ProductSize> sizes = objectMapper.readValue(command.getSize(), new TypeReference<>() {
            });
            List<ProductAbout> abouts = objectMapper.readValue(command.getAbout(), new TypeReference<>() {
            });
            if (command.getPrimaryImageIndex() == null) command.setPrimaryImageIndex((byte) 0);
            Product savedProduct = productRepository.save(command.toEntity());

            sizes.forEach(productSize -> productSize.setProductId(savedProduct.getProductId()));
            sizeRepository.saveAll(sizes);

            abouts.forEach(productAbout -> productAbout.setProductId(savedProduct.getProductId()));
            aboutRepository.saveAll(abouts);

            return successResponse();
        } catch (Exception e) {
            return null;
        }
    }

    public ResponseEntity<Response> deleteById(Long productId) {
        productRepository.deleteById(productId);
        sizeRepository.deleteByProductId(productId);
        return successResponse();
    }

    public ResponseEntity<Response> amountCheck(List<Long> productIds) {
        List<ProductAmountCheckDto> list = new ArrayList<>();
        for (Long productId : productIds) {
            Optional<Product> product = productRepository.findByProductId(productId);
            if (product.isPresent()) {
                ProductAmountCheckDto dto = new ProductAmountCheckDto();
                dto.setProductId(productId);
                dto.setAmount(product.get().getAmount());
                dto.setPrice(product.get().getPrice());
                Optional<List<ProductSize>> sizes = sizeRepository.findByProductId(productId);
                dto.setSizes(sizes.orElse(Collections.emptyList()));
                dto.setIsSized(!dto.getSizes().isEmpty());
                list.add(dto);
            }
        }
        return successResponse(new ProductAmountCheckListDto(list));
    }

    public ResponseEntity<Response> mostBuy() {
        List<Product> products = productRepository.getMostBuy();
        return successResponse(new ProductSearchDto(products));
    }

    public ResponseEntity<Response> newest() {
        List<Product> products = productRepository.getNewest();
        return successResponse(new ProductSearchDto(products));
    }

    private void updateProductDetails(Product product, ProductEditCommand command) {
        Optional.ofNullable(command.getTitle()).ifPresent(product::setTitle);
        Optional.ofNullable(command.getPrice()).ifPresent(product::setPrice);
        Optional.ofNullable(command.getAmount()).ifPresent(product::setAmount);
        Optional.ofNullable(command.getImageUrl()).filter(url -> !url.isEmpty()).ifPresent(product::setImageUrl);
        Optional.ofNullable(command.getPrimaryImageIndex()).ifPresent(product::setPrimaryImageIndex);
        Optional.ofNullable(command.getDescription()).ifPresent(product::setDescription);
        Optional.ofNullable(command.getOffPercent()).ifPresent(product::setOffPercent);
        if (command.getCategoryId() != null) {
            product.setCategory(new Category(command.getCategoryId()));
        }
    }

    public ResponseEntity<Response> like(Long productId) {
        productRepository.like(productId);
        return successResponse();
    }

    public ResponseEntity<Response> removeLike(Long productId) {
        productRepository.removeLike(productId);
        return successResponse();
    }

    public ResponseEntity<Response> searchByName(String searchQuery, Long userId) {
        if (userId != null) userProductSearchService.save(searchQuery, userId);
        List<Product> products = productRepository.searchByName(searchQuery);
        return successResponse(new ProductSearchDto(products));
    }
}
