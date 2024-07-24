package com.shop.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shop.command.ProductAddCommand;
import com.shop.command.ProductEditCommand;
import com.shop.dto.ProductAmountCheckDto;
import com.shop.dto.ProductDto;
import com.shop.model.Product;
import com.shop.model.ProductSize;
import com.shop.repository.ProductRepository;
import com.shop.repository.ProductSizeRepository;
import com.shop.shared.classes.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductSizeRepository sizeRepository;
    private final ObjectMapper objectMapper;

    @Autowired
    public ProductService(ProductRepository productRepository, ProductSizeRepository sizeRepository, ObjectMapper objectMapper) {
        this.productRepository = productRepository;
        this.sizeRepository = sizeRepository;
        this.objectMapper = objectMapper;
    }

    public ResponseEntity<Response> getAll(Long categoryId, Byte sort) {
        Response response = new Response();
        try {
            List<Product> products = new ArrayList<>();
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
                productDto.setProductSize(productSizes.orElse(Collections.emptyList()));
                dto.add(productDto);
            }

            Map<String, List<ProductDto>> map = new HashMap<>();
            map.put("products", dto);
            response.setData(map);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return buildErrorResponse(e);
        }
    }

    public ResponseEntity<Response> edit(ProductEditCommand command) {
        Response response = new Response();
        try {
            Optional<Product> product = productRepository.findByProductId(command.getProductId());
            if (product.isPresent()) {
                updateProductDetails(product.get(), command);
                productRepository.save(product.get());
                if (command.getSize() != null) {
                    List<ProductSize> sizes = objectMapper.readValue(command.getSize(), new TypeReference<List<ProductSize>>() {});
                    sizes.forEach(productSize -> productSize.setProductId(command.getProductId()));
                    sizeRepository.saveAll(sizes);
                }
                return ResponseEntity.ok(response);
            } else {
                response.setMessage("wrong productId");
                response.setSuccess(false);
                return ResponseEntity.ok(response);
            }
        } catch (Exception e) {
            return buildErrorResponse(e);
        }
    }

    public ResponseEntity<Response> add(ProductAddCommand command) {
        Response response = new Response();
        try {
            List<ProductSize> sizes = objectMapper.readValue(command.getSize(), new TypeReference<List<ProductSize>>() {});
            if (command.getPrimaryImageIndex() == null) command.setPrimaryImageIndex((byte) 0);
            Product savedProduct = productRepository.save(command.toEntity());

            sizes.forEach(productSize -> productSize.setProductId(savedProduct.getProductId()));
            sizeRepository.saveAll(sizes);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return buildErrorResponse(e);
        }
    }

    public ResponseEntity<Response> deleteById(Long productId) {
        Response response = new Response();
        try {
            productRepository.deleteById(productId);
            sizeRepository.deleteByProductId(productId);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return buildErrorResponse(e);
        }
    }

    public ResponseEntity<Response> amountCheck(List<Long> productIds) {
        Response response = new Response();
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
            response.setData(map);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return buildErrorResponse(e);
        }
    }

    public ResponseEntity<Response> retrieve(Long productId) {
        Response response = new Response();
        try {
            Optional<Product> product = productRepository.findByProductId(productId);
            if (product.isPresent()) {
                ProductDto productDto = new ProductDto();
                productDto.setProduct(product.get());
                Optional<List<ProductSize>> sizes = sizeRepository.findByProductId(productId);
                productDto.setProductSize(sizes.orElse(Collections.emptyList()));

                Map<String, ProductDto> map = new HashMap<>();
                map.put("product", productDto);
                response.setData(map);
                return ResponseEntity.ok(response);
            } else {
                response.setMessage("wrong productId!");
                response.setSuccess(true);
                return ResponseEntity.ok(response);
            }
        } catch (Exception e) {
            return buildErrorResponse(e);
        }
    }

    public ResponseEntity<Response> mostBuy() {
        Response response = new Response();
        try {
            List<Product> products = productRepository.getMostBuy();
            Map<String, List<Product>> map = new HashMap<>();
            map.put("products", products);
            response.setData(map);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return buildErrorResponse(e);
        }
    }

    public ResponseEntity<Response> newest() {
        Response response = new Response();
        try {
            List<Product> products = productRepository.getNewest();
            Map<String, List<Product>> map = new HashMap<>();
            map.put("products", products);
            response.setData(map);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return buildErrorResponse(e);
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

    private ResponseEntity<Response> buildErrorResponse(Exception e) {
        Response response = new Response();
        response.setSuccess(false);
        response.setMessage(e.getMessage());
        return ResponseEntity.ok(response);
    }
}
