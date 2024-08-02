package com.shop.controller;

import com.shop.service.UserProductSearchService;
import com.shop.shared.classes.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("api/v1/user-product-search")
@CrossOrigin(origins = "*")
public class UserProductSearchController {
    private final UserProductSearchService service;

    @Autowired
    public UserProductSearchController(UserProductSearchService service) {
        this.service = service;
    }

    @GetMapping(path = "{userId}")
    public ResponseEntity<Response> getById(@PathVariable Long userId) {
        return service.getByUserId(userId);
    }
}
