package com.shop.service;

import com.shop.repository.ProductAboutRepository;
import com.shop.shared.classes.BaseService;
import com.shop.shared.classes.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductAboutService extends BaseService {

    private final ProductAboutRepository repository;

    public ResponseEntity<Response> deleteById(Long aboutId) {
        repository.deleteById(aboutId);
        return successResponse();
    }
}
