package com.shop.controller;

import com.shop.service.ProductSizeService;
import com.shop.shared.classes.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("api/v1/size")
@CrossOrigin(origins = "*")
public class ProductSizeController {

    private final ProductSizeService productSizeService;

    @Autowired
    public ProductSizeController(ProductSizeService productSizeService) {
        this.productSizeService = productSizeService;
    }

    @DeleteMapping(path = "delete/{sizeId}")
    public ResponseEntity<Response> deleteById(@PathVariable Long sizeId) {
        return productSizeService.deleteById(sizeId);
    }
}
