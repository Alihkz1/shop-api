package com.shop.service;

import com.shop.repository.ProductColorRepository;
import com.shop.shared.classes.BaseService;
import com.shop.shared.classes.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductColorService extends BaseService {
    private final ProductColorRepository repository;

    public ResponseEntity<Response> deleteById(Long sizeId) {
        repository.deleteById(sizeId);
        return successResponse();
    }
}
