package com.shop.repository;

import com.shop.dto.OrderListDto;
import com.shop.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query(value = "select * from orders o JOIN shop_card s " +
            "on o.shop_card_id = s.shop_card_id " +
            "where o.user_id = :userId", nativeQuery = true)
    Optional<List<OrderListDto>> findByUserId(Long userId);
}
