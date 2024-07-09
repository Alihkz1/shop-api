package com.shop.repository;

import com.shop.model.ProductSize;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProductSizeRepository extends JpaRepository<ProductSize, Long> {
    @Modifying
    @Transactional
    @Query(value = "delete from product_size where product_id = :productId", nativeQuery = true)
    void deleteByProductId(Long productId);

    @Modifying
    @Transactional
    @Query(value = "delete from product_size where id = :sizeId", nativeQuery = true)
    void deleteById(Long sizeId);

    Optional<List<ProductSize>> findByProductId(Long productId);
}
