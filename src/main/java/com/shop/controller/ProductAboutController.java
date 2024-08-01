package com.shop.controller;

import com.shop.service.ProductAboutService;
import com.shop.shared.classes.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("api/v1/about")
@CrossOrigin(origins = "*")
public class ProductAboutController {
    private final ProductAboutService service;

    @Autowired
    public ProductAboutController(ProductAboutService service) {
        this.service = service;
    }

    @DeleteMapping(path = "delete/{aboutId}")
    public ResponseEntity<Response> deleteById(@PathVariable Long aboutId) {
        return service.deleteById(aboutId);
    }
}