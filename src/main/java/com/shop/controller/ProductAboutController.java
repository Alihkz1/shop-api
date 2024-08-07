package com.shop.controller;

import com.shop.service.ProductAboutService;
import com.shop.shared.classes.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("api/v1/about")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class ProductAboutController {

    private final ProductAboutService service;

    @DeleteMapping(path = "delete/{aboutId}")
    public ResponseEntity<Response> deleteById(@PathVariable Long aboutId) {
        return service.deleteById(aboutId);
    }
}