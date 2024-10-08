package com.shop.repository;

import com.shop.dto.ProductRetrieve;
import com.shop.model.Product;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.redis.core.RedisTemplate;
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

    @Query(value = "select * from product" +
            " where category_id = :categoryId" +
            " order by product_id asc", nativeQuery = true)
    List<Product> getAll(Long categoryId);

    @Query(value = "select p.*, c.category_name from product p " +
            "join category c on p.category_id = c.category_id " +
            "where product_id = :productId" , nativeQuery = true)
    Optional<ProductRetrieve> retrieve(Long productId);

    @Query(value = "select * from product" +
            " where title like %:searchQuery%" +
            " order by product_id desc", nativeQuery = true)
    List<Product> searchByName(String searchQuery);

    @Query(value = "select * from product" +
            " where category_id = :categoryId" +
            " order by price desc", nativeQuery = true)
    List<Product> getAllExpensive(Long categoryId);

    @Query(value = "select * from product" +
            " where category_id = :categoryId" +
            " order by price asc", nativeQuery = true)
    List<Product> getAllCheap(Long categoryId);

    @Query(value = "select * from product" +
            " where category_id = :categoryId" +
            " order by buy_count desc", nativeQuery = true)
    List<Product> getAllMostBuy(Long categoryId);

    @Modifying
    @Transactional
    @Query(value = "update product set amount = amount - :lostAmount where product_id = :productId", nativeQuery = true)
    void decreaseProductAmount(Long productId, Long lostAmount);

    @Modifying
    @Transactional
    @Query(value = "update product set buy_count = buy_count + :buyCount where product_id = :productId", nativeQuery = true)
    void increaseProductBuyCount(Long productId, Long buyCount);

    @Modifying
    @Transactional
    @Query(value = "update product set likes = likes + 1 where product_id = :productId", nativeQuery = true)
    void like(Long productId);

    @Modifying
    @Transactional
    @Query(value = "update product set likes = likes - 1 where product_id = :productId", nativeQuery = true)
    void removeLike(Long productId);


    @Query(value = "select * from product" +
            " order by buy_count desc limit 3", nativeQuery = true)
    List<Product> getMostBuy();

    @Query(value = "select * from product" +
            " order by product_id desc limit 3", nativeQuery = true)
    List<Product> getNewest();
}
