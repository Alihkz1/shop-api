package com.shop.service;

import com.shop.command.ProductAddCommand;
import com.shop.command.ProductEditCommand;
import com.shop.dto.ProductAmountCheckDto;
import com.shop.model.Product;
import com.shop.repository.ProductRepository;
import com.shop.shared.classes.Response;
import com.shop.shared.enums.SortProduct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ResponseEntity<Response> getAll(Long categoryId, Byte sort) {
        Response response = new Response();
        /*todo: imp sort by most bought*/
        Map<String, List<Product>> map = new HashMap<>();
        if (sort == null) {
            map.put("products", productRepository.getAll(categoryId));
        } else if (sort == 1) {
            map.put("products", productRepository.getAllExpensive(categoryId));
        } else if (sort == 2) {
            map.put("products", productRepository.getAllCheap(categoryId));
        } else if (sort == 3) {
            map.put("products", productRepository.getAllMostBuy(categoryId));
        }
        response.setData(map);
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<Response> edit(ProductEditCommand command) {
        Response response = new Response();
        Optional<Product> product = productRepository.findByProductId(command.getProductId());

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
            if (command.getImageUrl() != null && !command.getImageUrl().equals("")) {
                product.get().setImageUrl(command.getImageUrl());
            }
            if (command.getCategoryId() != null) {
                product.get().setCategoryId(command.getCategoryId());
            }
            if (command.getDescription() != null) {
                product.get().setDescription(command.getDescription());
            }
            if (command.getSize() != null) {
                product.get().setSize(command.getSize());
            }
            productRepository.save(product.get());
            return ResponseEntity.ok(response);
        } else {
            response.setMessage("wrong productId");
            response.setSuccess(false);
            return ResponseEntity.ok(response);
        }
    }

    public ResponseEntity<Response> add(ProductAddCommand command) {
        Response response = new Response();
        try {
            productRepository.save(command.toEntity());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.setSuccess(false);
            response.setMessage(e.getMessage());
            return ResponseEntity.ok(response);
        }
    }

    public ResponseEntity<Response> deleteById(Long categoryId) {
        Response response = new Response();
        try {
            productRepository.deleteById(categoryId);
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
        try {
            Optional<Product> product = productRepository.findByProductId(productId);
            if (product.isPresent()) {
                Map<String, Product> map = new HashMap<>();
                map.put("product", product.get());
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

}
