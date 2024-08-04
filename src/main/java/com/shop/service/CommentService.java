package com.shop.service;

import com.shop.command.CommentAddCommand;
import com.shop.command.CommentEditCommand;
import com.shop.dto.CommentDto;
import com.shop.dto.CommentListDto;
import com.shop.dto.UserCommentDto;
import com.shop.model.Comment;
import com.shop.repository.CommentRepository;
import com.shop.repository.UserRepository;
import com.shop.shared.classes.BaseService;
import com.shop.shared.classes.Response;
import com.shop.shared.classes.UserThread;
import com.shop.shared.enums.ErrorMessagesEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CommentService extends BaseService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository, UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
    }

    public ResponseEntity<Response> getAll() {
        List<Comment> comments = commentRepository.getAll();
        List<CommentDto> dtoList = new ArrayList<>();
        comments.forEach(comment -> {
            CommentDto dto = new CommentDto();
            dto.setComment(comment);
            dto.setUser(userRepository.findByUserId(comment.getUserId()));
            dtoList.add(dto);
        });
        return successResponse(new CommentListDto(dtoList));
    }

    public ResponseEntity<Response> getUserAllComments(Long userId) {
        List<Comment> comments = commentRepository.getUserAllComments(userId);
        return successResponse(new UserCommentDto(comments));
    }

    public ResponseEntity<Response> add(CommentAddCommand command) {
        try {
            commentRepository.save(command.toEntity(UserThread.getUserId()));
            return successResponse();
        } catch (Exception e) {
            return serverErrorResponse(e.getMessage());
        }
    }

    public ResponseEntity<Response> edit(CommentEditCommand command) {
        Optional<Comment> commentOptional = commentRepository.findByCommentId(command.getCommentId());
        if (commentOptional.isEmpty()) {
            return badRequestResponse(ErrorMessagesEnum.NO_COMMENT_FOUND);
        } else {
            Comment comment = commentOptional.get();
            if (command.getMessage() != null) {
                comment.setMessage(command.getMessage());
            }
            if (command.getRead() != null) {
                comment.setRead(command.getRead());
            }
            commentRepository.save(comment);
            return successResponse("comment successfully updated!");
        }
    }

    public ResponseEntity<Response> deleteById(Long commentId) {
        try {
            commentRepository.deleteById(commentId);
            return successResponse();
        } catch (Exception e) {
            return serverErrorResponse(e.getMessage());
        }
    }
}
