package com.shop.repository;

import com.shop.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Optional<Comment> findByCommentId(Long commentId);

    @Query(value = "select * from comment order by comment_id asc", nativeQuery = true)
    List<Comment> getAll();

    @Query(value = "select * from comment where user_id = :userId order by comment_id asc", nativeQuery = true)
    List<Comment> getUserAllComments(Long userId);

    @Modifying
    @Transactional
    @Query(value = "delete from comment where user_id = :userId", nativeQuery = true)
    List<Comment> deleteByUserId(Long userId);
}
