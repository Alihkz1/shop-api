package com.shop.service;

import com.shop.command.CategoryAddCommand;
import com.shop.command.CategoryEditCommand;
import com.shop.dto.*;
import com.shop.model.Category;
import com.shop.model.Product;
import com.shop.model.ProductSize;
import com.shop.repository.CategoryRepository;
import com.shop.repository.ProductRepository;
import com.shop.repository.ProductSizeRepository;
import com.shop.shared.Exceptions.BadRequestException;
import com.shop.shared.classes.BaseService;
import com.shop.shared.classes.Response;
import com.shop.shared.enums.ErrorMessagesEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService extends BaseService {
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final ProductSizeRepository sizeRepository;

    @Cacheable(value = "category-lightlist")
    public CategoryLightListDto lightList() {
        List<CategoryLightListClass> list = categoryRepository.lightList()
                .stream()
                .map(data -> new CategoryLightListClass(
                        data.getCategoryId(),
                        data.getCategoryName(),
                        data.getImageUrl(),
                        data.getProductCount())
                )
                .collect(Collectors.toList());
        return new CategoryLightListDto(list);
    }

    public ResponseEntity<Response> list() {
        List<CategoryList> finalDto = new ArrayList<>();
        categoryRepository.findAll().forEach((category -> {
            CategoryList dto = new CategoryList();
            dto.setCategoryId(category.getCategoryId());
            dto.setCategoryName(category.getCategoryName());
            dto.setImageUrl(category.getImageUrl());
            finalDto.add(dto);
        }));

        finalDto.forEach(dto -> {
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

        List<CategoryList> sorted = finalDto.stream().sorted(Comparator.comparing(CategoryList::getCategoryId)).collect(Collectors.toList());

        return successResponse(new CategoryListDto(sorted));
    }

    @Transactional
    public ResponseEntity<Response> edit(CategoryEditCommand command) {
        Optional<Category> category = categoryRepository.findByCategoryId(command.getCategoryId());

        if (category.isPresent()) {
            if (command.getCategoryName() != null) {
                category.get().setCategoryName(command.getCategoryName());
            }
            if (command.getImageUrl() != null && !command.getImageUrl().isEmpty()) {
                category.get().setImageUrl(command.getImageUrl());
            }
            categoryRepository.save(category.get());
            return successResponse();
        } else {
            throw new BadRequestException(ErrorMessagesEnum.NO_CATEGORIES_FOUND.getMessage());
        }
    }

    public ResponseEntity<Response> add(CategoryAddCommand command) {
        categoryRepository.save(command.toEntity());
        return successResponse();
    }

    @Transactional
    public ResponseEntity<Response> deleteById(Long categoryId) {
        /*todo: delete sizes */
        productRepository.deleteByCategoryId(categoryId);
        categoryRepository.deleteById(categoryId);
        return successResponse();
    }
}
