package com.shop.service;

import com.shop.repository.ProductSizeRepository;
import com.shop.shared.classes.BaseService;
import com.shop.shared.classes.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

@Repository
public class ProductSizeService extends BaseService {
    private final ProductSizeRepository repository;

    @Autowired
    public ProductSizeService(ProductSizeRepository repository) {
        this.repository = repository;
    }

    public ResponseEntity<Response> deleteById(Long sizeId) {
        try {
            repository.deleteById(sizeId);
            return successResponse();
        } catch (Exception e) {
            return serverErrorResponse(e.getMessage());
        }
    }
}
