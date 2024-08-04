package com.shop.repository;

import com.shop.dto.CardProductIdAmount;
import com.shop.model.ShopCard;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ShopCardRepository extends JpaRepository<ShopCard, Long> {
    @Query(value = "select * from shop_card where user_id = :userId and paid = 0 order by shop_card_id desc", nativeQuery = true)
    Optional<List<ShopCard>> findByUserId(Long userId);

    @Query(value = "select * from shop_card where order_id = :orderId order by shop_card_id desc", nativeQuery = true)
    Optional<List<ShopCard>> findByOrderId(Long orderId);

    @Modifying
    @Transactional
    @Query(value = "update shop_card set paid = 1, order_id = :orderId where paid = 0 and user_id = :userId", nativeQuery = true)
    void payShopCards(Long orderId, Long userId);

    @Query(value = "select s.product_id, s.amount, p.price from shop_card s " +
            "join product p on s.product_id = p.product_id " +
            "where s.order_id = :orderId order by shop_card_id desc", nativeQuery = true)
    List<CardProductIdAmount> findProductIdAndAmountByOrderId(Long orderId);
}
