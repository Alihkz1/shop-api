package com.shop.service;

import com.shop.command.CategoryAddCommand;
import com.shop.command.CategoryEditCommand;
import com.shop.model.Category;
import com.shop.repository.CategoryRepository;
import com.shop.shared.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public ResponseEntity<Response> getAll() {
        Response response = new Response();
        Map<String, List<Category>> map = new HashMap<>();
        map.put("categories", categoryRepository.findAll());
        response.setData(map);
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<Response> edit(CategoryEditCommand command) {
        Response response = new Response();
        Optional<Category> category = categoryRepository.findByCategoryId(command.getCategoryId());

        if (category.isPresent()) {
            category.get().setCategoryName(command.getCategoryName());
            categoryRepository.save(category.get());
            return ResponseEntity.ok(response);
        } else {
            response.setMessage("wrong categoryId");
            response.setSuccess(false);
            return ResponseEntity.ok(response);
        }
    }

    public ResponseEntity<Response> add(CategoryAddCommand command) {
        Response response = new Response();
        try {
            categoryRepository.save(command.toEntity());
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
            categoryRepository.deleteById(categoryId);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.setSuccess(false);
            response.setMessage(e.getMessage());
            return ResponseEntity.ok(response);
        }
    }


}
