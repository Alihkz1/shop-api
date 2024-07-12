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

    @Autowired
    public ProductService(ProductRepository productRepository, ProductSizeRepository sizeRepository) {
        this.productRepository = productRepository;
        this.sizeRepository = sizeRepository;
    }

    public ResponseEntity<Response> getAll(Long categoryId, Byte sort) {
        Response response = new Response();
        List<ProductDto> dto = new ArrayList<>();
        Map<String, List<ProductDto>> map = new HashMap<>();
        List<Product> products = new ArrayList<>();
        if (sort == null) {
            products = productRepository.getAll(categoryId);
        } else if (sort == 1) {
            products = productRepository.getAllExpensive(categoryId);
        } else if (sort == 2) {
            products = productRepository.getAllCheap(categoryId);
        } else if (sort == 3) {
            products = productRepository.getAllMostBuy(categoryId);
        }
        products.forEach(product -> {
            ProductDto productDto = new ProductDto();
            productDto.setProduct(product);
            Optional<List<ProductSize>> productSizes = sizeRepository.findByProductId(product.getProductId());
            productDto.setProductSize(productSizes.get());
            dto.add(productDto);
        });
        map.put("products", dto);
        response.setData(map);
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<Response> edit(ProductEditCommand command) {
        Response response = new Response();
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            Optional<Product> product = productRepository.findByProductId(command.getProductId());
            List<ProductSize> sizes = objectMapper.readValue(command.getSize(), new TypeReference<List<ProductSize>>() {
            });
            if (product.isPresent()) {
                if (command.getTitle() != null) {
                    product.get().setTitle(command.getTitle());
                }
                if (command.getPrice() != null) {
                    product.get().setPrice(command.getPrice());
                }
                if (command.getAmount() != null) {
                    product.get().setAmount(command.getAmount());
                }
                if (command.getImageUrl() != null && !command.getImageUrl().isEmpty()) {
                    product.get().setImageUrl(command.getImageUrl());
                }
                if (command.getCategoryId() != null) {
                    product.get().setCategoryId(command.getCategoryId());
                }
                if (command.getDescription() != null) {
                    product.get().setDescription(command.getDescription());
                }
                if (command.getSize() != null) {
                    sizes.forEach(productSize -> productSize.setProductId(command.getProductId()));
                    sizeRepository.saveAll(sizes);
                }
                productRepository.save(product.get());
                return ResponseEntity.ok(response);
            } else {
                response.setMessage("wrong productId");
                response.setSuccess(false);
                return ResponseEntity.ok(response);
            }
        } catch (Exception e) {
            response.setSuccess(false);
            response.setMessage(e.getMessage());
            return ResponseEntity.ok(response);
        }
    }

    public ResponseEntity<Response> add(ProductAddCommand command) {
        Response response = new Response();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            List<ProductSize> sizes = objectMapper.readValue(command.getSize(), new TypeReference<List<ProductSize>>() {
            });
            Product savedProduct = productRepository.save(command.toEntity());

            sizes.forEach(productSize -> productSize.setProductId(savedProduct.getProductId()));
            sizeRepository.saveAll(sizes);

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            response.setSuccess(false);
            response.setMessage(e.getMessage());
            return ResponseEntity.ok(response);
        }
    }

    public ResponseEntity<Response> deleteById(Long productId) {
        Response response = new Response();
        try {
            productRepository.deleteById(productId);
            sizeRepository.deleteByProductId(productId);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.setSuccess(false);
            response.setMessage(e.getMessage());
            return ResponseEntity.ok(response);
        }
    }

    public ResponseEntity<Response> amountCheck(List<Long> productIds) {
        Response response = new Response();
        try {
            List<ProductAmountCheckDto> list = new ArrayList<>();
            Map<String, List<ProductAmountCheckDto>> map = new HashMap<>();

            productIds.forEach(productId -> {
                Optional<Product> product = productRepository.findByProductId(productId);
                ProductAmountCheckDto dto = new ProductAmountCheckDto();
                dto.setProductId(productId);
                dto.setAmount(product.get().getAmount());
                dto.setPrice(product.get().getPrice());
                Optional<List<ProductSize>> sizes = sizeRepository.findByProductId(productId);
                dto.setSizes(sizes.get());
                if (sizes.get().isEmpty()) dto.setIsSized(false);
                else dto.setIsSized(true);
                list.add(dto);
            });
            map.put("products", list);
            response.setData(map);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.setSuccess(false);
            response.setMessage(e.getMessage());
            return ResponseEntity.ok(response);
        }
    }


    public ResponseEntity<Response> retrieve(Long productId) {
        Response response = new Response();
        ProductDto productDto = new ProductDto();
        try {
            Optional<Product> product = productRepository.findByProductId(productId);
            if (product.isPresent()) {
                productDto.setProduct(product.get());
                Map<String, ProductDto> map = new HashMap<>();
                Optional<List<ProductSize>> sizes = sizeRepository.findByProductId(productId);
                productDto.setProductSize(sizes.get());
                map.put("product", productDto);
                response.setData(map);
            } else {
                response.setMessage("wrong productId!");
                response.setSuccess(true);
                response.setData(new Product());
            }
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.setSuccess(false);
            response.setMessage(e.getMessage());
            return ResponseEntity.ok(response);
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
            response.setSuccess(false);
            response.setMessage(e.getMessage());
            return ResponseEntity.ok(response);
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
            response.setSuccess(false);
            response.setMessage(e.getMessage());
            return ResponseEntity.ok(response);
        }
    }
}
