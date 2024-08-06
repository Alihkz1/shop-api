package com.shop.service;

import com.shop.command.SaveProductCommentCommand;
import com.shop.repository.ProductCommentRepository;
import com.shop.shared.classes.BaseService;
import com.shop.shared.classes.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ProductCommentService extends BaseService {

    private final ProductCommentRepository repository;

    @Autowired
    public ProductCommentService(ProductCommentRepository repository) {
        this.repository = repository;
    }

    public ResponseEntity<Response> getByProductId(Long productId) {
        try {
            return successResponse(repository.getByProductId(productId));
        } catch (Exception e) {
            return serverErrorResponse(e.getMessage());
        }
    }

    public ResponseEntity<Response> deleteByCommentId(Long commentId) {
        try {
            repository.deleteById(commentId);
            return successResponse();
        } catch (Exception e) {
            return serverErrorResponse(e.getMessage());
        }
    }

    public ResponseEntity<Response> acceptByAdmin(Long commentId) {
        try {
            repository.acceptByAdmin(commentId);
            return successResponse();
        } catch (Exception e) {
            return serverErrorResponse(e.getMessage());
        }
    }

    public ResponseEntity<Response> saveComment(SaveProductCommentCommand command) {
        try {
            repository.save(command.toEntity());
            return successResponse();
        } catch (Exception e) {
            return serverErrorResponse(e.getMessage());
        }
    }
}
