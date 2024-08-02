package com.shop.repository;

import com.shop.model.ProductAbout;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProductAboutRepository extends JpaRepository<ProductAbout, Long> {
    @Modifying
    @Transactional
    @Query(value = "delete from product_about where id = :aboutId", nativeQuery = true)
    void deleteById(Long aboutId);

    Optional<List<ProductAbout>> findByProductId(Long productId);
}
