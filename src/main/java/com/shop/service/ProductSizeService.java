package com.shop.service;

import com.shop.repository.ProductSizeRepository;
import com.shop.shared.classes.BaseService;
import com.shop.shared.classes.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductSizeService extends BaseService {

    private final ProductSizeRepository repository;

    public ResponseEntity<Response> deleteById(Long sizeId) {
        repository.deleteById(sizeId);
        return successResponse();
    }
}
