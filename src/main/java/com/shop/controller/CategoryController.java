package com.shop.controller;

import com.shop.command.CategoryAddCommand;
import com.shop.command.CategoryEditCommand;
import com.shop.service.CategoryService;
import com.shop.shared.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("api/v1/category")
@CrossOrigin(origins = "*")
public class CategoryController {
    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping(path = "list")
    public ResponseEntity<Response> getAll() {
        return categoryService.getAll();
    }

    @PutMapping(path = "edit")
    public ResponseEntity<Response> edit(@RequestBody CategoryEditCommand command) {
        return categoryService.edit(command);
    }

    @PostMapping(path = "add")
    public ResponseEntity<Response> add(@RequestBody CategoryAddCommand command) {
        return categoryService.add(command);
    }

    @DeleteMapping(path = "delete/{categoryId}")
    public ResponseEntity<Response> deleteById(@PathVariable Long categoryId) {
        return categoryService.deleteById(categoryId);
    }
}