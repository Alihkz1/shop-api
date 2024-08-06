package com.shop.controller;

import com.shop.command.SaveProductCommentCommand;
import com.shop.service.ProductCommentService;
import com.shop.shared.classes.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("api/v1/product-comment")
@CrossOrigin(origins = "*")
public class ProductCommentController {
    private final ProductCommentService service;

    @Autowired
    public ProductCommentController(ProductCommentService service) {
        this.service = service;
    }

    @GetMapping(path = "{productId}")
    public ResponseEntity<Response> getByProductId(@PathVariable Long productId) {
        return service.getByProductId(productId);
    }

    @DeleteMapping(path = "{commentId}")
    public ResponseEntity<Response> deleteByCommentId(@PathVariable Long commentId) {
        return service.deleteByCommentId(commentId);
    }

    @PutMapping(path = "{commentId}")
    public ResponseEntity<Response> acceptByAdmin(@PathVariable Long commentId) {
        return service.acceptByAdmin(commentId);
    }

    @PostMapping()
    public ResponseEntity<Response> saveComment(SaveProductCommentCommand command) {
        return service.saveComment(command);
    }
}
