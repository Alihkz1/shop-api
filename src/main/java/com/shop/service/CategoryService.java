package com.shop.service;

import com.shop.command.CategoryAddCommand;
import com.shop.command.CategoryEditCommand;
import com.shop.dto.CategoryLightListDto;
import com.shop.dto.CategoryListDto;
import com.shop.dto.ProductDto;
import com.shop.model.Category;
import com.shop.model.Product;
import com.shop.model.ProductSize;
import com.shop.repository.CategoryRepository;
import com.shop.repository.ProductRepository;
import com.shop.repository.ProductSizeRepository;
import com.shop.shared.classes.BaseService;
import com.shop.shared.classes.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CategoryService extends BaseService {
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
        Map<String, List<CategoryLightListDto>> map = new HashMap<>();
        map.put("categories", categoryRepository.lightList());
        return successResponse(map);
    }

    public ResponseEntity<Response> list() {
        List<CategoryListDto> finalDto = new ArrayList<>();
        categoryRepository.findAll().stream().forEach((category -> {
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

        List<CategoryListDto> sorted = finalDto.stream().sorted(Comparator.comparing(CategoryListDto::getCategoryId)).collect(Collectors.toList());

        Map<String, List<CategoryListDto>> map = new HashMap<>();
        map.put("categories", sorted);
        return successResponse(map);
    }

    public ResponseEntity<Response> edit(CategoryEditCommand command) {
        Optional<Category> category = categoryRepository.findByCategoryId(command.getCategoryId());

        if (category.isPresent()) {
            if (command.getCategoryName() != null) {
                category.get().setCategoryName(command.getCategoryName());
            }
            if (command.getImageUrl() != null && !command.getImageUrl().equals("")) {
                category.get().setImageUrl(command.getImageUrl());
            }
            categoryRepository.save(category.get());
            return successResponse();
        } else {
            return errorResponse("wrong categoryId");
        }
    }

    public ResponseEntity<Response> add(CategoryAddCommand command) {
        try {
            categoryRepository.save(command.toEntity());
            return successResponse();
        } catch (Exception e) {
            return errorResponse(e.getMessage());
        }
    }

    public ResponseEntity<Response> deleteById(Long categoryId) {
        try {
            /*todo: delete sizes */
            productRepository.deleteByCategoryId(categoryId);
            categoryRepository.deleteById(categoryId);
            return successResponse();
        } catch (Exception e) {
            return errorResponse(e.getMessage());
        }
    }
}
