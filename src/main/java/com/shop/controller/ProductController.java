package com.shop.controller;

import com.shop.command.ProductAddCommand;
import com.shop.command.ProductEditCommand;
import com.shop.query.ProductAmountCheckQuery;
import com.shop.query.ProductGetAllQuery;
import com.shop.query.ProductSearchQuery;
import com.shop.service.ProductService;
import com.shop.shared.classes.Response;
import com.shop.shared.classes.UserThread;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("api/v1/product")
@CrossOrigin(origins = "*")
public class ProductController {
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping(path = "list")
    public ResponseEntity<Response> getAll(@ModelAttribute ProductGetAllQuery query) {
        return productService.getAll(query.getCategoryId(), query.getSort());
    }

    @GetMapping(path = "retrieve/{productId}")
    public ResponseEntity<Response> retrieve(@PathVariable Long productId) {
        return productService.retrieve(productId);
    }

    @GetMapping(path = "most-buy")
    public ResponseEntity<Response> mostBuy() {
        return productService.mostBuy();
    }

    @GetMapping(path = "search")
    public ResponseEntity<Response> searchByName(@ModelAttribute ProductSearchQuery query) {
        return productService.searchByName(query.getQ(), UserThread.getUserId());
    }

    @GetMapping(path = "newest")
    public ResponseEntity<Response> newest() {
        return productService.newest();
    }

    @PutMapping(path = "edit")
    public ResponseEntity<Response> edit(@RequestBody ProductEditCommand command) {
        return productService.edit(command);
    }

    @PostMapping(path = "add")
    public ResponseEntity<Response> add(@RequestBody ProductAddCommand command) {
        return productService.add(command);
    }

    @DeleteMapping(path = "delete/{productId}")
    public ResponseEntity<Response> deleteById(@PathVariable Long productId) {
        return productService.deleteById(productId);
    }

    @GetMapping(path = "amount-check")
    public ResponseEntity<Response> amountCheck(@ModelAttribute ProductAmountCheckQuery query) {
        return productService.amountCheck(query.getIds());
    }

    @PutMapping(path = "like/{productId}")
    public ResponseEntity<Response> like(@PathVariable Long productId) {
        return productService.like(productId);
    }

    @PutMapping(path = "remove-like/{productId}")
    public ResponseEntity<Response> removeLike(@PathVariable Long productId) {
        return productService.removeLike(productId);
    }
}
