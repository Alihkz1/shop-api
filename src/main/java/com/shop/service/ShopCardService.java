package com.shop.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shop.command.ShopCardModifyCommand;
import com.shop.dto.ShopCardDto;
import com.shop.model.ShopCard;
import com.shop.repository.ShopCardRepository;
import com.shop.shared.classes.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ShopCardService {

    private final ShopCardRepository shopCardRepository;

    @Autowired
    public ShopCardService(ShopCardRepository shopCardRepository) {
        this.shopCardRepository = shopCardRepository;
    }

    public ResponseEntity<Response> getUserCard(Long userId) {
        /*todo: mpaid = 0 should come*/
        Response response = new Response();
        try {
            Optional<ShopCard> shopCard = shopCardRepository.findByUserId(userId);
            if (shopCard.isEmpty()) {
                response.setMessage("no data for this user!");
            } else {
//                Map<String, List<ShopCardDto>> map = new HashMap<>();
//                /*todo*/
//                String stringProducts = shopCard.get().getProducts();
//                ObjectMapper objectMapper = new ObjectMapper();
//                List<ShopCardDto> products = objectMapper.readValue(stringProducts, new TypeReference<List<ShopCardDto>>() {
//                });
//                products.stream().forEach(shopCardDto -> {
//                    shopCardDto.setUserId(userId);
//                    shopCardDto.setShopCardId(shopCard.get().getShopCardId());
//                });
//                map.put("card", products);
//                response.setData(map);
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
            Optional<ShopCard> shopCard = shopCardRepository.findByUserId(userId);
            if (shopCard.isEmpty()) {
                response.setMessage("no data for this user!");
                response.setData(0);
            } else {
                /*todo*/
//                String stringProducts = shopCard.get().getProducts();
//                ObjectMapper objectMapper = new ObjectMapper();
//                List<ShopCardDto> products = objectMapper.readValue(stringProducts, new TypeReference<List<ShopCardDto>>() {
//                });
//                response.setData(products.size());
            }
        } catch (Exception e) {
            response.setSuccess(false);
            response.setMessage(e.getMessage());
        }
        return ResponseEntity.ok(response);
    }


    public ResponseEntity<Response> modify(ShopCardModifyCommand command) {
        Response response = new Response();
        ObjectMapper objectMapper = new ObjectMapper();
        Optional<ShopCard> userCard = shopCardRepository.findByUserId(command.getUserId());
        try {
//            if (userCard.isPresent()) {
//                userCard.get().setProducts(objectMapper.writeValueAsString(command.getProducts()));
//                shopCardRepository.save(userCard.get());
//            } else {
//                ShopCard shopCard = new ShopCard();
//                shopCard.setUserId(command.getUserId());
//                shopCard.setProducts(objectMapper.writeValueAsString(command.getProducts()));
//                shopCardRepository.save(shopCard);
//            }
            /*todo*/
        } catch (Exception e) {
            response.setSuccess(false);
            response.setMessage(e.getMessage());
        }

        return ResponseEntity.ok(response);
    }

    public void shopCardIsPaid(Long shopCardId) {
        shopCardRepository.shopCardIsPaid(shopCardId);
    }
}
