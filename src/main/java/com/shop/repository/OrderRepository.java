package com.shop.repository;

import com.shop.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query(value = "select * from orders where user_id = :userId", nativeQuery = true)
    Optional<List<Order>> findByUserId(Long userId);
}
