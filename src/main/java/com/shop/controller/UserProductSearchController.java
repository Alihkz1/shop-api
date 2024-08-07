package com.shop.controller;

import com.shop.service.UserProductSearchService;
import com.shop.shared.classes.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("api/v1/user-product-search")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class UserProductSearchController {

    private final UserProductSearchService service;

    @GetMapping()
    public ResponseEntity<Response> getById() {
        return service.getByUserId();
    }

    @DeleteMapping(path = "delete/{searchId}")
    public ResponseEntity<Response> deleteById(@PathVariable Long searchId) {
        return service.deleteById(searchId);
    }
}
