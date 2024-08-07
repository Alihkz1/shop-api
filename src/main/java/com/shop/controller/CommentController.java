package com.shop.controller;

import com.shop.command.CommentAddCommand;
import com.shop.command.CommentEditCommand;
import com.shop.service.CommentService;
import com.shop.shared.classes.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("api/v1/comment")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @GetMapping(path = "list")
    public ResponseEntity<Response> getAll() {
        return commentService.getAll();
    }

    @GetMapping(path = "user/{userId}")
    public ResponseEntity<Response> getUserAllComments(@PathVariable Long userId) {
        return commentService.getUserAllComments(userId);
    }

    @PutMapping(path = "edit")
    public ResponseEntity<Response> edit(@RequestBody CommentEditCommand command) {
        return commentService.edit(command);
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
