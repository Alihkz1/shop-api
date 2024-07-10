package com.shop.repository;

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
    @Query(value = "select * from shop_card where user_id = :userId and paid = 0", nativeQuery = true)
    Optional<List<ShopCard>> findByUserId(Long userId);

    @Modifying
    @Transactional
    @Query(value = "update shop_card set paid = 1 where shop_card_id = :shopCardId", nativeQuery = true)
    void payShopCard(Long shopCardId);
}
