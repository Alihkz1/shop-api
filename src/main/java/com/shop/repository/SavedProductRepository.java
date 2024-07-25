package com.shop.repository;

import com.shop.model.SavedProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SavedProductRepository extends JpaRepository<SavedProduct, Long> {

    Optional<List<SavedProduct>> findByUserId(Long userId);

}
