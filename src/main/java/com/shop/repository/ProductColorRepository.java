package com.shop.repository;

import com.shop.model.ProductAbout;
import com.shop.model.ProductColor;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProductColorRepository extends JpaRepository<ProductColor, Long> {
    @Modifying
    @Transactional
    @Query(value = "delete from product_color where id = :colorId", nativeQuery = true)
    void deleteById(Long colorId);

    Optional<List<ProductColor>> findByProductId(Long productId);
}
