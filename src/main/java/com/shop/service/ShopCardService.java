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
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ShopCardService extends BaseService {

    private final ShopCardRepository shopCardRepository;
    private final ProductRepository productRepository;
    private final ProductSizeRepository sizeRepository;

    public ResponseEntity<Response> getUserCardLight() {
        /*todo: if product deleted then should not include here!*/
        Optional<List<ShopCard>> userShopCards = shopCardRepository.findByUserId(UserThread.getUserId());
        return successResponse(new ShopCardLightListDto(userShopCards.get()));
    }

    public ResponseEntity<Response> getUserCard() {
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
    }

    public ResponseEntity<Response> getUserCardLength() {
        Optional<List<ShopCard>> userShopCards = shopCardRepository.findByUserId(UserThread.getUserId());
        return successResponse(userShopCards.get().size());
    }

    public ResponseEntity<Response> modify(ShopCardModifyCommand command) {
        ShopCard card = shopCardRepository.save(command.toEntity());
        return successResponse(new ShopCardModifyDto(card));
    }

    public ResponseEntity<Response> modifyAll(List<ShopCardModifyCommand> list) {
        List<ShopCard> cards = list.stream().map(ShopCardModifyCommand::toEntity).toList();
        List<ShopCard> saved = shopCardRepository.saveAll(cards);
        return successResponse(new ShopCardModifyAllDto(saved));
    }

    public ResponseEntity<Response> deleteById(Long shopCardId) {
        shopCardRepository.deleteById(shopCardId);
        return successResponse();
    }

    public void payShopCards(Long shopCardId, Long userId) {
        shopCardRepository.payShopCards(shopCardId, userId);
    }
}
