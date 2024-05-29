package com.shop.controller;

import com.shop.command.ProductAddCommand;
import com.shop.command.ProductEditCommand;
import com.shop.service.ProductService;
import com.shop.shared.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("api/v1/product")
@CrossOrigin(origins = "*")
public class ProductController {
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping(path = "list")
    public ResponseEntity<Response> getAll() {
        return productService.getAll();
    }

    @PutMapping(path = "edit")
    public ResponseEntity<Response> edit(@RequestBody ProductEditCommand command) {
        return productService.edit(command);
    }

    @PostMapping(path = "add")
    public ResponseEntity<Response> add(@RequestBody ProductAddCommand command) {
        return productService.add(command);
    }

    @DeleteMapping(path = "delete/{productId}")
    public ResponseEntity<Response> deleteById(@PathVariable Long productId) {
        return productService.deleteById(productId);
    }
}
