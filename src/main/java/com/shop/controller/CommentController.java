package com.shop.controller;

import com.shop.command.CommentAddCommand;
import com.shop.service.CommentService;
import com.shop.shared.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("api/v1/comment")
@CrossOrigin(origins = "*")
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping(path = "list")
    public ResponseEntity<Response> getAll() {
        return commentService.getAll();
    }

    @PostMapping(path = "add")
    public ResponseEntity<Response> add(@RequestBody CommentAddCommand command) {
        return commentService.add(command);
    }

    @DeleteMapping(path = "delete/{commentId}")
    public ResponseEntity<Response> deleteById(@PathVariable Long commentId) {
        return commentService.deleteById(commentId);
    }
}
