package com.shop.service;

import com.shop.repository.ProductAboutRepository;
import com.shop.shared.classes.BaseService;
import com.shop.shared.classes.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ProductAboutService extends BaseService {
    private final ProductAboutRepository repository;

    @Autowired
    public ProductAboutService(ProductAboutRepository repository) {
        this.repository = repository;
    }

    public ResponseEntity<Response> deleteById(Long aboutId) {
        try {
            repository.deleteById(aboutId);
            return successResponse();
        } catch (Exception e) {
            return serverErrorResponse(e.getMessage());
        }
    }
}
