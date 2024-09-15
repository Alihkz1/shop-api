package com.shop.controller;

import com.shop.service.ProductColorService;
import com.shop.shared.classes.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("api/v1/color")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class ProductColorController {

    private final ProductColorService service;

    @DeleteMapping(path = "delete/{aboutId}")
    public ResponseEntity<Response> deleteById(@PathVariable Long aboutId) {
        return service.deleteById(aboutId);
    }
}
