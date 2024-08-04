package com.shop.service;

import com.shop.command.ShopCardModifyCommand;
import com.shop.dto.*;
import com.shop.model.Product;
import com.shop.model.ProductSize;
import com.shop.model.ShopCard;
import com.shop.repository.ProductRepository;
import com.shop.repository.ProductSizeRepository;
import com.shop.repository.ShopCardRepository;
import com.shop.shared.classes.BaseService;
import com.shop.shared.classes.Response;
import com.shop.shared.classes.UserThread;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ShopCardService extends BaseService {

    private final ShopCardRepository shopCardRepository;
    private final ProductRepository productRepository;
    private final ProductSizeRepository sizeRepository;

    @Autowired
    public ShopCardService(ShopCardRepository shopCardRepository, ProductRepository productRepository, ProductSizeRepository sizeRepository) {
        this.shopCardRepository = shopCardRepository;
        this.productRepository = productRepository;
        this.sizeRepository = sizeRepository;
    }

    public ResponseEntity<Response> getUserCardLight() {
        try {
            /*todo: if product deleted then should not include here!*/
            Optional<List<ShopCard>> userShopCards = shopCardRepository.findByUserId(UserThread.getUserId());
            return successResponse(new ShopCardLightListDto(userShopCards.get()));

        } catch (Exception e) {
            return serverErrorResponse(e.getMessage());
        }
    }

    public ResponseEntity<Response> getUserCard() {
        try {
            Optional<List<ShopCard>> userShopCards = shopCardRepository.findByUserId(UserThread.getUserId());
            List<ShopCardDto> list = new ArrayList<>();
            userShopCards.get().forEach(shopCard -> {
                ShopCardDto shopCardDto = new ShopCardDto();
                ProductDto productDto = new ProductDto();
                shopCardDto.setShopCard(shopCard);
                Optional<Product> product = productRepository.findByProductId(shopCard.getProductId());
                if (product.isPresent()) {
                    productDto.setProduct(product.get());
                    List<ProductSize> sizes = sizeRepository.findByProductId(shopCard.getProductId()).get();
                    productDto.setProductSize(sizes);
                } else return;
                shopCardDto.setProduct(productDto);
                list.add(shopCardDto);
            });
            return successResponse(new ShopCardListDto(list));
        } catch (Exception e) {
            return serverErrorResponse(e.getMessage());
        }
    }

    public ResponseEntity<Response> getUserCardLength() {
        try {
            Optional<List<ShopCard>> userShopCards = shopCardRepository.findByUserId(UserThread.getUserId());
            return successResponse(userShopCards.get().size());
        } catch (Exception e) {
            return serverErrorResponse(e.getMessage());
        }
    }

    public ResponseEntity<Response> modify(ShopCardModifyCommand command) {
        try {
            ShopCard card = shopCardRepository.save(command.toEntity());
            return successResponse(new ShopCardModifyDto(card));
        } catch (Exception e) {
            return serverErrorResponse(e.getMessage());
        }
    }

    public ResponseEntity<Response> modifyAll(List<ShopCardModifyCommand> list) {
        try {
            List<ShopCard> cards = list.stream().map(ShopCardModifyCommand::toEntity).toList();
            List<ShopCard> saved = shopCardRepository.saveAll(cards);
            return successResponse(new ShopCardModifyAllDto(saved));
        } catch (Exception e) {
            return serverErrorResponse(e.getMessage());
        }
    }

    public ResponseEntity<Response> deleteById(Long shopCardId) {
        try {
            shopCardRepository.deleteById(shopCardId);
        } catch (Exception e) {
            return serverErrorResponse(e.getMessage());
        }
        return successResponse();
    }

    public void payShopCards(Long shopCardId, Long userId) {
        shopCardRepository.payShopCards(shopCardId, userId);
    }
}
