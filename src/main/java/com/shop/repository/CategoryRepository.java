package com.shop.repository;

import com.shop.dto.CategoryLightList;
import com.shop.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByCategoryId(Long categoryId);

    @Query(value = "select * from category order by category_id asc", nativeQuery = true)
    List<Category> getAll();

    @Query(value = "SELECT c.*, COALESCE(COUNT(p.product_id), 0) AS productCount " +
            "FROM category c " +
            "LEFT JOIN product p ON c.category_id = p.category_id " +
            "GROUP BY c.category_id " +
            "ORDER BY c.category_id ASC", nativeQuery = true)
    List<CategoryLightList> lightList();
}
