package com.shop.service;

import com.shop.command.SavedProductAddCommand;
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

    @Autowired
    public SavedProductService(SavedProductRepository repository) {
        this.repository = repository;
    }

    public ResponseEntity<Response> getAll(Long userId) {
        Optional<List<SavedProduct>> userSavedItems = repository.findByUserId(userId);
        Map map = new HashMap<>();
        map.put("items", userSavedItems);
        return successResponse(map);
    }

    public ResponseEntity<Response> add(SavedProductAddCommand command) {
        try {
            repository.save(command.toEntity());
            return successResponse();
        } catch (Exception e) {
            return errorResponse(e.getMessage());
        }
    }

    public ResponseEntity<Response> deleteById(Long id) {
        try {
            repository.deleteById(id);
            return successResponse();
        } catch (Exception e) {
            return errorResponse(e.getMessage());
        }
    }
}
