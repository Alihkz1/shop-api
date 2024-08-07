package com.shop.controller;

import com.shop.command.ShopCardModifyCommand;
import com.shop.service.ShopCardService;
import com.shop.shared.classes.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("api/v1/card")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class ShopCardController {

    private final ShopCardService shopCardService;

    @GetMapping("light")
    public ResponseEntity<Response> getUserCardLight() {
        return shopCardService.getUserCardLight();
    }

    @GetMapping("user")
    public ResponseEntity<Response> getUserCard() {
        return shopCardService.getUserCard();
    }

    @GetMapping("length")
    public ResponseEntity<Response> getUserCardLength() {
        return shopCardService.getUserCardLength();
    }

    @PostMapping("modify")
    public ResponseEntity<Response> modify(@RequestBody ShopCardModifyCommand command) {
        return shopCardService.modify(command);
    }

    @PostMapping("modifyAll")
    public ResponseEntity<Response> modifyAll(@RequestBody List<ShopCardModifyCommand> list) {
        return shopCardService.modifyAll(list);
    }

    @DeleteMapping("delete/{shopCardId}")
    public ResponseEntity<Response> deleteById(@PathVariable Long shopCardId) {
        return shopCardService.deleteById(shopCardId);
    }
}
