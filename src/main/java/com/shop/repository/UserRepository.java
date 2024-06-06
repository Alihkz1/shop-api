package com.shop.repository;

import com.shop.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    public Optional<User> findByEmail(String email);

    public Optional<User> findByUserId(Long userId);

    @Query(value = "select * from users where LOWER(email) = :email", nativeQuery = true)
    User login(String email);

}
