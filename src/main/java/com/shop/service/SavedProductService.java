package com.shop.service;

import com.shop.command.SavedProductCrudCommand;
import com.shop.dto.ProductDto;
import com.shop.dto.SavedProductListDto;
import com.shop.model.SavedProduct;
import com.shop.repository.SavedProductRepository;
import com.shop.shared.classes.BaseService;
import com.shop.shared.classes.Response;
import com.shop.shared.classes.UserThread;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SavedProductService extends BaseService {

    private final SavedProductRepository repository;
    private final ProductService productService;

    @Autowired
    public SavedProductService(
            SavedProductRepository repository,
            ProductService productService) {
        this.repository = repository;
        this.productService = productService;
    }

    public ResponseEntity<Response> getAll() {
        Optional<List<SavedProduct>> userSavedItems = repository.findByUserId(UserThread.getUserId());
        List<Long> productIds = userSavedItems.get().stream()
                .map(SavedProduct::getProductId)
                .toList();
        List<ProductDto> products = productService.listByIds(productIds);
        return successResponse(new SavedProductListDto(products));
    }

    public ResponseEntity<Response> add(SavedProductCrudCommand command) {
        repository.save(command.toEntity());
        return successResponse();
    }

    public ResponseEntity<Response> delete(SavedProductCrudCommand command) {
        repository.deleteSaved(command.getUserId(), command.getProductId());
        return successResponse();
    }

    public ResponseEntity<Response> isSaved(SavedProductCrudCommand command) {
        Optional<SavedProduct> item = repository.findByUserIdAndProductId(command.getUserId(), command.getProductId());
        if (item.isPresent()) {
            return successResponse(item.get());
        } else return successResponse();
    }
}
