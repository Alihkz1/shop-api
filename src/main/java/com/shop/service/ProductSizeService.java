package com.shop.service;

import com.shop.repository.ProductSizeRepository;
import com.shop.shared.classes.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

@Repository
public class ProductSizeService {
    private final ProductSizeRepository repository;

    @Autowired
    public ProductSizeService(ProductSizeRepository repository) {
        this.repository = repository;
    }

    public ResponseEntity<Response> deleteById(Long sizeId) {
        Response response = new Response();
        try {
            repository.deleteById(sizeId);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.setSuccess(false);
            response.setMessage(e.getMessage());
            return ResponseEntity.ok(response);
        }
    }
}
