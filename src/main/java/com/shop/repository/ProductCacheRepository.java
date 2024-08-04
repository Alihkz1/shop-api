package com.shop.repository;

import com.shop.model.Product;
import com.shop.shared.classes.UserThread;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

@Repository
public class ProductCacheRepository {
    private RedisTemplate redisTemplate;

    public Product findById(Integer id) {
        return (Product) redisTemplate.opsForHash().get(
                Objects.requireNonNull(UserThread.getUserId()),
                id
        );
    }

    public List<Product> findAll() {
        return redisTemplate.opsForHash().values(Objects.requireNonNull(UserThread.getUserId()));
    }

    public void save(Product product) {
        redisTemplate.opsForHash().put(
                Objects.requireNonNull(UserThread.getUserId()),
                Objects.requireNonNull(UserThread.getUserId()),
                product
        );
    }

    public void deleteProduct(Integer id) {
        redisTemplate.opsForHash().delete(
                Objects.requireNonNull(UserThread.getUserId()),
                id
        );
    }
}
