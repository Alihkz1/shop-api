package com.shop.controller;

import com.shop.command.CategoryAddCommand;
import com.shop.command.CategoryEditCommand;
import com.shop.service.CategoryService;
import com.shop.shared.classes.BaseService;
import com.shop.shared.classes.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("api/v1/category")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class CategoryController extends BaseService {

    private final CategoryService categoryService;

    @GetMapping(path = "list")
    public ResponseEntity<Response> list() {
        return categoryService.list();
    }

    @GetMapping(path = "light-list")
    public ResponseEntity<Response> lightList() {
        return successResponse(categoryService.lightList());
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
