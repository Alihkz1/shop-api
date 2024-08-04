package com.shop.repository;

import com.shop.dto.OrderList;
import com.shop.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query(value = "select * from orders " +
            "where user_id = :userId order by order_id desc", nativeQuery = true)
    Optional<List<OrderList>> findByUserId(Long userId);

    @Query(value = "select * from orders " +
            "where user_id = :userId and status = :status order by order_id desc", nativeQuery = true)
    Optional<List<OrderList>> findByUserId(Long userId, Byte status);

    @Query(value = "select * from orders " +
            "where status = :status " +
            "order by order_id desc", nativeQuery = true)
    Optional<List<OrderList>> adminList(Byte status);

    @Query(value = "select * from orders " +
            "order by order_id desc", nativeQuery = true)
    Optional<List<OrderList>> adminList();

    Optional<Order> findByOrderId(Long orderId);

    @Query(value = "select * from orders " +
            "where code = :code order by order_id desc", nativeQuery = true)
    Optional<OrderList> findByCode(String code);

    @Query(value = "select order_id from orders where code = :orderCode", nativeQuery = true)
    Long getOrderIdByOrderCode(String orderCode);
}
