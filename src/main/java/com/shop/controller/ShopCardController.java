package com.shop.controller;

import com.shop.command.ShopCardModifyCommand;
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

    @GetMapping("user/{userId}")
    public ResponseEntity<Response> getUserCard(@PathVariable Long userId) {
        return shopCardService.getUserCard(userId);
    }

    @GetMapping("length/{userId}")
    public ResponseEntity<Response> getUserCardLength(@PathVariable Long userId) {
        return shopCardService.getUserCardLength(userId);
    }

    @PostMapping("modify")
    public ResponseEntity<Response> modify(@RequestBody ShopCardModifyCommand command) {
        return shopCardService.modify(command);
    }
}
