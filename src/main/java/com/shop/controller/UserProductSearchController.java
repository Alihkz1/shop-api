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

    @GetMapping()
    public ResponseEntity<Response> getById() {
        return service.getByUserId();
    }

    @DeleteMapping(path = "delete/{searchId}")
    public ResponseEntity<Response> deleteById(@PathVariable Long searchId) {
        return service.deleteById(searchId);
    }
}
