package com.shop.repository;

import com.shop.model.ShopCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShopCardRepository extends JpaRepository<ShopCard, Long> {
    Optional<ShopCard> findByUserId(Long userId);
}
