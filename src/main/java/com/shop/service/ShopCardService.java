package com.shop.service;

import com.shop.command.ShopCardModifyCommand;
import com.shop.dto.ProductDto;
import com.shop.dto.ShopCardDto;
import com.shop.model.ShopCard;
import com.shop.repository.ProductRepository;
import com.shop.repository.ProductSizeRepository;
import com.shop.repository.ShopCardRepository;
import com.shop.shared.classes.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ShopCardService {

    private final ShopCardRepository shopCardRepository;
    private final ProductRepository productRepository;
    private final ProductSizeRepository sizeRepository;

    @Autowired
    public ShopCardService(ShopCardRepository shopCardRepository, ProductRepository productRepository, ProductSizeRepository sizeRepository) {
        this.shopCardRepository = shopCardRepository;
        this.productRepository = productRepository;
        this.sizeRepository = sizeRepository;
    }

    public ResponseEntity<Response> getUserCardLight(Long userId) {
        Response response = new Response();
        Map<String, List<ShopCard>> map = new HashMap<>();
        try {
            Optional<List<ShopCard>> userShopCards = shopCardRepository.findByUserId(userId);
            if (userShopCards.isEmpty()) {
                response.setMessage("no data for this user!");
            } else {
                map.put("cards", userShopCards.get());
                response.setData(map);
            }
        } catch (Exception e) {
            response.setSuccess(false);
            response.setMessage(e.getMessage());
        }
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<Response> getUserCard(Long userId) {
        Response response = new Response();
        Map<String, List<ShopCardDto>> map = new HashMap<>();
        try {
            Optional<List<ShopCard>> userShopCards = shopCardRepository.findByUserId(userId);
            if (userShopCards.isEmpty()) {
                response.setMessage("no data for this user!");
            } else {
                List<ShopCardDto> list = new ArrayList<>();
                userShopCards.get().forEach(shopCard -> {
                    ShopCardDto shopCardDto = new ShopCardDto();
                    ProductDto productDto = new ProductDto();
                    shopCardDto.setShopCard(shopCard);
                    /*todo: code below if not found check*/
                    productDto.setProduct(productRepository.findByProductId(shopCard.getProductId()).get());
                    productDto.setProductSize(sizeRepository.findByProductId(shopCard.getProductId()).get());
                    shopCardDto.setProduct(productDto);
                    list.add(shopCardDto);
                });
                map.put("cards", list);
                response.setData(map);
            }
        } catch (Exception e) {
            response.setSuccess(false);
            response.setMessage(e.getMessage());
        }
        return ResponseEntity.ok(response);
    }


    public ResponseEntity<Response> getUserCardLength(Long userId) {
        Response<Integer> response = new Response();
        try {
            Optional<List<ShopCard>> userShopCards = shopCardRepository.findByUserId(userId);
            if (userShopCards.isEmpty()) {
                response.setMessage("no data for this user!");
                response.setData(0);
            } else {
                response.setData(userShopCards.get().size());
            }
        } catch (Exception e) {
            response.setSuccess(false);
            response.setMessage(e.getMessage());
        }
        return ResponseEntity.ok(response);
    }


    public ResponseEntity<Response> modify(ShopCardModifyCommand command) {
        Response response = new Response();
        Map<String, ShopCard> map = new HashMap<>();
        try {
            ShopCard card = shopCardRepository.save(command.toEntity());
            map.put("card", card);
            response.setData(map);
        } catch (Exception e) {
            response.setSuccess(false);
            response.setMessage(e.getMessage());
        }

        return ResponseEntity.ok(response);
    }

    public ResponseEntity<Response> modifyAll(List<ShopCardModifyCommand> list) {
        Response response = new Response();
        Map<String, List<ShopCard>> map = new HashMap<>();

        try {
            List<ShopCard> cards = list.stream().map(ShopCardModifyCommand::toEntity).toList();
            List<ShopCard> saved = shopCardRepository.saveAll(cards);
            map.put("cards", saved);
            response.setData(map);
        } catch (Exception e) {
            response.setSuccess(false);
            response.setMessage(e.getMessage());
        }

        return ResponseEntity.ok(response);
    }

    public ResponseEntity<Response> deleteById(Long shopCardId) {
        Response response = new Response();
        try {
            shopCardRepository.deleteById(shopCardId);
        } catch (Exception e) {
            response.setSuccess(false);
            response.setMessage(e.getMessage());
        }
        return ResponseEntity.ok(response);
    }

    public void payShopCard(Long shopCardId) {
        shopCardRepository.payShopCard(shopCardId);
    }
}
