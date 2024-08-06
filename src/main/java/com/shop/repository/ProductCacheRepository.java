package com.shop.repository;

import com.shop.model.Product;
import com.shop.shared.classes.UserThread;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

@Repository
public class ProductCacheRepository {
    private RedisTemplate template;

    public Product findById(Integer id) {
        return (Product) template.opsForHash().get(
                Objects.requireNonNull(UserThread.getUserId()),
                id
        );
    }

    public List<Product> findAll() {
        return template.opsForHash().values(Objects.requireNonNull(UserThread.getUserId()));
    }

    public void save(Product product) {
        template.opsForHash().put(
                Objects.requireNonNull(UserThread.getUserId()),
                Objects.requireNonNull(UserThread.getUserId()),
                product
        );
    }

    public void deleteProduct(Integer id) {
        template.opsForHash().delete(
                Objects.requireNonNull(UserThread.getUserId()),
                id
        );
    }
}
