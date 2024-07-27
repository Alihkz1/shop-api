package com.shop.service;

import com.shop.command.SavedProductCrudCommand;
import com.shop.dto.ProductDto;
import com.shop.model.SavedProduct;
import com.shop.repository.SavedProductRepository;
import com.shop.shared.classes.BaseService;
import com.shop.shared.classes.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class SavedProductService extends BaseService {

    private final SavedProductRepository repository;
    private final ProductService productService;

    @Autowired
    public SavedProductService(
            SavedProductRepository repository,
            ProductService productService) {
        this.repository = repository;
        this.productService = productService;
    }

    public ResponseEntity<Response> getAll(Long userId) {
        Optional<List<SavedProduct>> userSavedItems = repository.findByUserId(userId);
        Map<String, List<ProductDto>> map = new HashMap<>();
        List<Long> productIds = userSavedItems.get().stream()
                .map(SavedProduct::getProductId)
                .toList();
        List<ProductDto> products =
                productService.listByIds(productIds);
        map.put("products", products);
        return successResponse(map);
    }

    public ResponseEntity<Response> add(SavedProductCrudCommand command) {
        try {
            repository.save(command.toEntity());
            return successResponse();
        } catch (Exception e) {
            return serverErrorResponse(e.getMessage());
        }
    }

    public ResponseEntity<Response> delete(SavedProductCrudCommand command) {
        try {
            repository.deleteSaved(command.getUserId(), command.getProductId());
            return successResponse();
        } catch (Exception e) {
            return serverErrorResponse(e.getMessage());
        }
    }

    public ResponseEntity<Response> isSaved(SavedProductCrudCommand command) {
        Optional<SavedProduct> item = repository.findByUserIdAndProductId(command.getUserId(), command.getProductId());
        if (item.isPresent()) {
            return successResponse(item.get());
        } else return successResponse();
    }
}