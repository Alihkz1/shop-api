package com.shop.repository;

import com.shop.model.Product;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByProductId(Long productId);
    @Modifying
    @Transactional
    @Query(value = "delete from product where category_id = :categoryId", nativeQuery = true)
    void deleteByCategoryId(Long categoryId);

    @Query(value = "select * from product order by product_id asc", nativeQuery = true)
    List<Product> getAll();
}
