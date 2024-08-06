package com.shop.repository;

import com.shop.model.SavedProduct;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SavedProductRepository extends JpaRepository<SavedProduct, Long> {

    Optional<List<SavedProduct>> findByUserId(Long userId);

    @Modifying
    @Transactional
    @Query(value = "delete from saved_product where user_id = :userId and product_id = :productId",nativeQuery = true)
    void deleteSaved(Long userId, Long productId);

    @Query(value = "select * from saved_product where user_id = :userId and product_id = :productId",nativeQuery = true)
    Optional<SavedProduct> findByUserIdAndProductId(Long userId, Long productId);

    @Modifying
    @Transactional
    @Query(value = "delete from saved_product where user_id = :userId", nativeQuery = true)
    void deleteByUserId(Long userId);
}
