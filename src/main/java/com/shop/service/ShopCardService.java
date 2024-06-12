package com.shop.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shop.command.ShopCardModifyCommand;
import com.shop.model.Product;
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
        Response response = new Response();
        try {
            Optional<ShopCard> shopCard = shopCardRepository.findByUserId(userId);
            if (shopCard.isEmpty()) {
                response.setMessage("no data for this user!");
            } else {
                Map<String, List<Product>> map = new HashMap<>();
                String stringProducts = shopCard.get().getProducts();
                ObjectMapper objectMapper = new ObjectMapper();
                List<Product> products = objectMapper.readValue(stringProducts, new TypeReference<List<Product>>(){});
                map.put("card", products);
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
            Optional<ShopCard> shopCard = shopCardRepository.findByUserId(userId);
            if (shopCard.isEmpty()) {
                response.setMessage("no data for this user!");
                response.setData(0);
            } else {
                String stringProducts = shopCard.get().getProducts();
                ObjectMapper objectMapper = new ObjectMapper();
                List<Product> products = objectMapper.readValue(stringProducts, new TypeReference<List<Product>>(){});
                response.setData(products.size());
            }
        } catch (Exception e) {
            response.setSuccess(false);
            response.setMessage(e.getMessage());
        }
        return ResponseEntity.ok(response);
    }


    public ResponseEntity<Response> modify(ShopCardModifyCommand command) {
        Response response = new Response();
        Optional<ShopCard> userCard = shopCardRepository.findByUserId(command.getUserId());
        try {
            if (userCard.isPresent()) {
                userCard.get().setProducts(command.getProducts());
                shopCardRepository.save(userCard.get());
            } else {
                ShopCard shopCard = new ShopCard();
                shopCard.setUserId(command.getUserId());
                shopCard.setProducts(command.getProducts());
                shopCardRepository.save(shopCard);
            }
        } catch (Exception e) {
            response.setSuccess(false);
            response.setMessage(e.getMessage());
        }

        return ResponseEntity.ok(response);
    }
}
