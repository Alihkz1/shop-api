package com.shop.service;

import com.shop.command.CategoryAddCommand;
import com.shop.command.CategoryEditCommand;
import com.shop.dto.CategoryListDto;
import com.shop.dto.ProductDto;
import com.shop.model.Category;
import com.shop.model.Product;
import com.shop.model.ProductSize;
import com.shop.repository.CategoryRepository;
import com.shop.repository.ProductRepository;
import com.shop.repository.ProductSizeRepository;
import com.shop.shared.classes.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final ProductSizeRepository sizeRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository, ProductRepository productRepository, ProductSizeRepository sizeRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
        this.sizeRepository = sizeRepository;
    }

    public ResponseEntity<Response> lightList() {
        Response response = new Response();
        Map<String, List<Category>> map = new HashMap<>();
        map.put("categories", categoryRepository.getAll());
        response.setData(map);
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<Response<CategoryListDto>> list() {
        Response response = new Response();
        List<CategoryListDto> finalDto = new ArrayList<>();
        categoryRepository.findAll().stream()
                .forEach((category -> {
                    CategoryListDto dto = new CategoryListDto();
                    dto.setCategoryId(category.getCategoryId());
                    dto.setCategoryName(category.getCategoryName());
                    dto.setImageUrl(category.getImageUrl());
                    finalDto.add(dto);
                }));

        finalDto.stream().forEach(dto -> {
            List<ProductDto> products = new ArrayList<>();
            List<Product> categoryProducts = productRepository.getAll(dto.getCategoryId());
            categoryProducts.forEach(product -> {
                ProductDto productDto = new ProductDto();
                Optional<List<ProductSize>> sizes = sizeRepository.findByProductId(product.getProductId());
                productDto.setProduct(product);
                productDto.setProductSize(sizes.get());
                products.add(productDto);
            });
            dto.setProducts(products);
        });

        List<CategoryListDto> sorted = finalDto.stream()
                .sorted(Comparator.comparing(CategoryListDto::getCategoryId))
                .collect(Collectors.toList());

        Map<String, List<CategoryListDto>> map = new HashMap<>();
        map.put("categories", sorted);
        response.setData(map);
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<Response> edit(CategoryEditCommand command) {
        Response response = new Response();
        Optional<Category> category = categoryRepository.findByCategoryId(command.getCategoryId());

        if (category.isPresent()) {
            if (command.getCategoryName() != null) {
                category.get().setCategoryName(command.getCategoryName());
            }
            if (command.getImageUrl() != null && !command.getImageUrl().equals("")) {
                category.get().setImageUrl(command.getImageUrl());
            }
            categoryRepository.save(category.get());
            return ResponseEntity.ok(response);
        } else {
            response.setMessage("wrong categoryId");
            response.setSuccess(false);
            return ResponseEntity.ok(response);
        }
    }

    public ResponseEntity<Response> add(CategoryAddCommand command) {
        Response response = new Response();
        try {
            categoryRepository.save(command.toEntity());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.setSuccess(false);
            response.setMessage(e.getMessage());
            return ResponseEntity.ok(response);
        }
    }

    public ResponseEntity<Response> deleteById(Long categoryId) {
        Response response = new Response();
        try {
            productRepository.deleteByCategoryId(categoryId);
            categoryRepository.deleteById(categoryId);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.setSuccess(false);
            response.setMessage(e.getMessage());
            return ResponseEntity.ok(response);
        }
    }
}
