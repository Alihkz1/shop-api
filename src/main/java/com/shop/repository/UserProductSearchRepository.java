package com.shop.repository;

import com.shop.model.UserProductSearch;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserProductSearchRepository extends JpaRepository<UserProductSearch, Long> {
    @Query(value = "select * from user_product_search " +
            "where user_id = :userId", nativeQuery = true)
    Optional<List<UserProductSearch>> findByUserId(Long userId);

    void deleteById(Long itemId);

    @Query(value = "select * from user_product_search" +
            " where user_id = :userId and search = :searchQuery"
            , nativeQuery = true)
    Optional<UserProductSearch> findBySearch(Long userId, String searchQuery);

    @Modifying
    @Transactional
    @Query(value = "delete from user_product_search where user_id = :userId", nativeQuery = true)
    void deleteByUserId(Long userId);
}
