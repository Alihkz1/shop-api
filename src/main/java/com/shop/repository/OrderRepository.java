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
            "where o.user_id = :userId order by o.order_id desc", nativeQuery = true)
    Optional<List<OrderListDto>> findByUserId(Long userId);

    @Query(value = "select * from orders o JOIN shop_card s " +
            "on o.shop_card_id = s.shop_card_id " +
            "where o.user_id = :userId and o.status = :status order by o.order_id desc", nativeQuery = true)
    Optional<List<OrderListDto>> findByUserId(Long userId, Byte status);

    @Query(value = "select * from orders o JOIN shop_card s " +
            "on o.shop_card_id = s.shop_card_id " +
            "where o.status = :status " +
            "order by o.order_id desc", nativeQuery = true)
    Optional<List<OrderListDto>> adminList(Byte status);

    @Query(value = "select * from orders o JOIN shop_card s " +
            "on o.shop_card_id = s.shop_card_id " +
            "order by o.order_id desc", nativeQuery = true)
    Optional<List<OrderListDto>> adminList();

    Optional<Order> findByOrderId(Long orderId);

    @Query(value = "select * from orders o JOIN shop_card s " +
            "on o.shop_card_id = s.shop_card_id " +
            "where o.code = :code order by o.order_id desc", nativeQuery = true)
    Optional<OrderListDto> findByCode(String code);

    @Query(value = "select s.products from orders o JOIN shop_card s " +
            "on o.shop_card_id = s.shop_card_id " +
            "where s.shop_card_id = :shopCardId", nativeQuery = true)
    String getOrderProductsByShopCardId(Long shopCardId);

    @Query(value = "select o.code from orders o JOIN shop_card s " +
            "on o.shop_card_id = s.shop_card_id " +
            "where s.shop_card_id = :shopCardId", nativeQuery = true)
    String getOrderCodeByShopCardId(Long shopCardId);

    @Query(value = "select order_id from orders where code = :orderCode", nativeQuery = true)
    Long getOrderIdByOrderCode(String orderCode);
}
