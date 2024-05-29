package com.shop.service;

import com.shop.command.CommentAddCommand;
import com.shop.model.Comment;
import com.shop.repository.CommentRepository;
import com.shop.shared.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CommentService {
    private final CommentRepository commentRepository;

    @Autowired
    CommentService(CommentRepository commentRepository ) {
        this.commentRepository = commentRepository;
    }

    public ResponseEntity<Response> getAll() {
        Response response = new Response();
        Map<String, List<Comment>> map = new HashMap<>();
        map.put("comments", commentRepository.findAll());
        response.setData(map);
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<Response> add(CommentAddCommand command) {
        /*todo: increase comment length*/
        Response response = new Response();
        try {
            commentRepository.save(command.toEntity());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.setSuccess(false);
            response.setMessage(e.getMessage());
            return ResponseEntity.ok(response);
        }
    }

    public ResponseEntity<Response> deleteById(Long commentId) {
        Response response = new Response();
        try {
            commentRepository.deleteById(commentId);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.setSuccess(false);
            response.setMessage(e.getMessage());
            return ResponseEntity.ok(response);
        }
    }
}
