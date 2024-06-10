package com.shop.service;

import com.shop.command.CommentAddCommand;
import com.shop.command.CommentEditCommand;
import com.shop.dto.CommentDto;
import com.shop.model.Comment;
import com.shop.repository.CommentRepository;
import com.shop.repository.UserRepository;
import com.shop.shared.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    @Autowired
    CommentService(CommentRepository commentRepository, UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
    }

    public ResponseEntity<Response> getAll() {
        Response response = new Response();
        Map<String, List<CommentDto>> map = new HashMap<>();
        List<Comment> comments = commentRepository.getAll();
        List<CommentDto> dtoList = new ArrayList<>();
        comments.stream().forEach(comment -> {
            CommentDto dto = new CommentDto();
            dto.setComment(comment);
            dto.setUser(userRepository.findByUserId(comment.getUserId()));
            dtoList.add(dto);
        });
        map.put("comments", dtoList);
        response.setData(map);
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<Response> getUserAllComments(Long userId) {
        Response response = new Response();
        Map<String, List<Comment>> map = new HashMap<>();
        List<Comment> comments = commentRepository.getUserAllComments(userId);
        map.put("userComments", comments);
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

    public ResponseEntity<Response> edit(CommentEditCommand command) {
        Response response = new Response();
        Optional<Comment> commentOptional = commentRepository.findByCommentId(command.getCommentId());
        if (commentOptional.isEmpty()) {
            response.setMessage("wrong commentId");
            response.setSuccess(false);
        } else {
            if (command.getMessage() != null) {
                commentOptional.get().setMessage(command.getMessage());
            }
            if (command.getRead() != null) {
                commentOptional.get().setRead(command.getRead());
            }
            commentRepository.save(commentOptional.get());
            response.setMessage("comment successfully updated!");
        }
        return ResponseEntity.ok(response);
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
