package com.shop.controller;

import com.shop.service.ProductSizeService;
import com.shop.shared.classes.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("api/v1/size")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class ProductSizeController {

    private final ProductSizeService productSizeService;

    @DeleteMapping(path = "delete/{sizeId}")
    public ResponseEntity<Response> deleteById(@PathVariable Long sizeId) {
        return productSizeService.deleteById(sizeId);
    }
}
