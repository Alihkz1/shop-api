package com.shop.repository;

import com.shop.model.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    Optional<User> findByPhone(String phone);

    Optional<User> findByUserId(Long userId);

    @Query(value = "select * from users where LOWER(email) = :emailOrPhone or phone = :emailOrPhone", nativeQuery = true)
    User login(String emailOrPhone);

    @Transactional
    @Modifying
    @Query(value = "update users set login_count = login_count + 1 where user_id = :userId", nativeQuery = true)
    void updateLoginCountByUserId(Long userId);

    @Transactional
    @Modifying
    @Query(value = "update users set order_count = order_count + 1, total_buy = total_buy + :newOrderPrice where user_id = :userId", nativeQuery = true)
    void updateUserOrdersByUserId(Long userId, Long newOrderPrice);

    @Query(value = "select * from users order by user_id desc", nativeQuery = true)
    List<User> getAll();

}
