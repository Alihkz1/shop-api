package com.shop.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shop.command.ProductAddCommand;
import com.shop.command.ProductEditCommand;
import com.shop.dto.ProductAmountCheckDto;
import com.shop.dto.ProductDto;
import com.shop.dto.ProductRetrieveDto;
import com.shop.dto.ProductRetrieveDto2;
import com.shop.model.Product;
import com.shop.model.ProductAbout;
import com.shop.model.ProductSize;
import com.shop.repository.ProductAboutRepository;
import com.shop.repository.ProductRepository;
import com.shop.repository.ProductSizeRepository;
import com.shop.shared.classes.BaseService;
import com.shop.shared.classes.Response;
import com.shop.shared.enums.ErrorMessagesEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ProductService extends BaseService {

    private final ProductRepository productRepository;
    private final ProductSizeRepository sizeRepository;
    private final ProductAboutRepository aboutRepository;
    private final ObjectMapper objectMapper;

    @Autowired
    public ProductService(
            ObjectMapper objectMapper,
            ProductRepository productRepository,
            ProductSizeRepository sizeRepository,
            ProductAboutRepository aboutRepository
    ) {
        this.productRepository = productRepository;
        this.sizeRepository = sizeRepository;
        this.objectMapper = objectMapper;
        this.aboutRepository = aboutRepository;
    }

    public ResponseEntity<Response> getAll(Long categoryId, Byte sort) {
        try {
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
                productDto.setProduct(product);
                Optional<List<ProductSize>> productSizes = sizeRepository.findByProductId(product.getProductId());
                Optional<List<ProductAbout>> abouts = aboutRepository.findByProductId(product.getProductId());
                productDto.setProductSize(productSizes.orElse(Collections.emptyList()));
                productDto.setProductAbout(abouts.orElse(Collections.emptyList()));
                dto.add(productDto);
            }

            Map<String, List<ProductDto>> map = new HashMap<>();
            map.put("products", dto);
            return successResponse(map);
        } catch (Exception e) {
            return serverErrorResponse(e.getMessage());
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
                return badRequestResponse(ErrorMessagesEnum.NO_PRODUCTS_FOUND);
            }
        } catch (Exception e) {
            return serverErrorResponse(e.getMessage());
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
            return serverErrorResponse(e.getMessage());
        }
    }

    public ResponseEntity<Response> deleteById(Long productId) {
        try {
            productRepository.deleteById(productId);
            sizeRepository.deleteByProductId(productId);
            return successResponse();
        } catch (Exception e) {
            return serverErrorResponse(e.getMessage());
        }
    }

    public ResponseEntity<Response> amountCheck(List<Long> productIds) {
        try {
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

            Map<String, List<ProductAmountCheckDto>> map = new HashMap<>();
            map.put("products", list);
            return successResponse(map);
        } catch (Exception e) {
            return serverErrorResponse(e.getMessage());
        }
    }

    public ResponseEntity<Response> retrieve(Long productId) {
        try {
            Optional<ProductRetrieveDto> product = productRepository.retrieve(productId);
            if (product.isPresent()) {
                ProductRetrieveDto2 dto2 = new ProductRetrieveDto2();
                dto2.setProduct(product.get());
                Optional<List<ProductSize>> sizes = sizeRepository.findByProductId(productId);
                Optional<List<ProductAbout>> abouts = aboutRepository.findByProductId(productId);
                dto2.setProductSize(sizes.orElse(Collections.emptyList()));
                dto2.setProductAbout(abouts.orElse(Collections.emptyList()));
                Map<String, ProductRetrieveDto2> map = new HashMap<>();
                map.put("product", dto2);
                return successResponse(map);
            } else {
                return badRequestResponse(ErrorMessagesEnum.NO_PRODUCTS_FOUND);
            }
        } catch (Exception e) {
            return serverErrorResponse(e.getMessage());
        }
    }

    public ResponseEntity<Response> mostBuy() {
        try {
            List<Product> products = productRepository.getMostBuy();
            Map<String, List<Product>> map = new HashMap<>();
            map.put("products", products);
            return successResponse(map);
        } catch (Exception e) {
            return serverErrorResponse(e.getMessage());
        }
    }

    public ResponseEntity<Response> newest() {
        try {
            List<Product> products = productRepository.getNewest();
            Map<String, List<Product>> map = new HashMap<>();
            map.put("products", products);
            return successResponse(map);
        } catch (Exception e) {
            return serverErrorResponse(e.getMessage());
        }
    }

    private void updateProductDetails(Product product, ProductEditCommand command) {
        Optional.ofNullable(command.getTitle()).ifPresent(product::setTitle);
        Optional.ofNullable(command.getPrice()).ifPresent(product::setPrice);
        Optional.ofNullable(command.getAmount()).ifPresent(product::setAmount);
        Optional.ofNullable(command.getImageUrl()).filter(url -> !url.isEmpty()).ifPresent(product::setImageUrl);
        Optional.ofNullable(command.getPrimaryImageIndex()).ifPresent(product::setPrimaryImageIndex);
        Optional.ofNullable(command.getCategoryId()).ifPresent(product::setCategoryId);
        Optional.ofNullable(command.getDescription()).ifPresent(product::setDescription);
    }

    public ResponseEntity<Response> like(Long productId) {
        try {
            productRepository.like(productId);
            return successResponse();
        } catch (Exception e) {
            return serverErrorResponse(e.getMessage());
        }
    }

    public ResponseEntity<Response> removeLike(Long productId) {
        try {
            productRepository.removeLike(productId);
            return successResponse();
        } catch (Exception e) {
            return serverErrorResponse(e.getMessage());
        }
    }

    public ResponseEntity<Response> searchByName(String searchQuery) {
        Map<String, List<Product>> map = new HashMap<>();
        try {
            List<Product> products = productRepository.searchByName(searchQuery);
            map.put("products", products);
            return successResponse(map);
        } catch (Exception e) {
            return serverErrorResponse(e.getMessage());
        }

    }
}
