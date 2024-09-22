package com.shop.service;

import com.shop.command.CommentAddCommand;
import com.shop.command.CommentEditCommand;
import com.shop.dto.CommentDto;
import com.shop.dto.CommentListDto;
import com.shop.dto.UserCommentDto;
import com.shop.model.Comment;
import com.shop.repository.CommentRepository;
import com.shop.repository.UserRepository;
import com.shop.shared.Exceptions.BadRequestException;
import com.shop.shared.classes.BaseService;
import com.shop.shared.classes.Response;
import com.shop.shared.classes.UserThread;
import com.shop.shared.enums.ErrorMessagesEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class CommentService extends BaseService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

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
        commentRepository.save(command.toEntity(UserThread.getUserId()));
        return successResponse();
    }

    public ResponseEntity<Response> edit(CommentEditCommand command) {
        Optional<Comment> commentOptional = commentRepository.findByCommentId(command.getCommentId());
        if (commentOptional.isEmpty()) {
            throw new BadRequestException(ErrorMessagesEnum.NO_COMMENT_FOUND.getMessage());
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
        commentRepository.deleteById(commentId);
        return successResponse();
    }
}
