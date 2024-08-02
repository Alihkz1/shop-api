package com.shop.repository;

import com.shop.model.UserProductSearch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserProductSearchRepository extends JpaRepository<UserProductSearch, Long> {
    Optional<List<UserProductSearch>> findByUserId(Long userId);
}
