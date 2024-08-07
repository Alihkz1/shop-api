package com.shop.service;

import com.shop.command.SaveProductCommentCommand;
import com.shop.repository.ProductCommentRepository;
import com.shop.shared.classes.BaseService;
import com.shop.shared.classes.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductCommentService extends BaseService {

    private final ProductCommentRepository repository;

    public ResponseEntity<Response> getByProductId(Long productId) {
        return successResponse(repository.getByProductId(productId));
    }

    public ResponseEntity<Response> deleteByCommentId(Long commentId) {
        repository.deleteById(commentId);
        return successResponse();
    }

    public ResponseEntity<Response> acceptByAdmin(Long commentId) {
        repository.acceptByAdmin(commentId);
        return successResponse();
    }

    public ResponseEntity<Response> saveComment(SaveProductCommentCommand command) {
        repository.save(command.toEntity());
        return successResponse();
    }
}
