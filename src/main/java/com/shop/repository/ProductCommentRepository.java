package com.shop.repository;

import com.shop.model.ProductComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface ProductCommentRepository extends JpaRepository<ProductComment, Long> {
    @Transactional
    @Modifying
    @Query(value = "update product_comment set state = 1" +
            "where comment_id = :commentId"
            , nativeQuery = true)
    void acceptByAdmin(Long commentId);

    @Transactional
    @Modifying
    @Query(value = "selct * from product_comment" +
            " where product_id = :productId"
            , nativeQuery = true)
    Optional<List<ProductComment>> getByProductId(Long productId);

    @Modifying
    @Transactional
    @Query(value = "delete from product_comment where user_id = :userId", nativeQuery = true)
    void deleteByUserId(Long userId);
}
