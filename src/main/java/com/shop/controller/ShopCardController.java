package com.shop.controller;

import com.shop.service.ShopCardService;
import com.shop.shared.classes.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("api/v1/card")
@CrossOrigin(origins = "*")
public class ShopCardController {

    private final ShopCardService shopCardService;

    @Autowired
    public ShopCardController(ShopCardService shopCardService) {
        this.shopCardService = shopCardService;
    }

    @GetMapping("{userId}")
    public ResponseEntity<Response> getUserCard(@PathVariable Long userId) {
        return shopCardService.getUserCard(userId);
    }

//    @PostMapping("add")
//    public ResponseEntity<Response> add(@RequestBody) {
//
//    }
}
