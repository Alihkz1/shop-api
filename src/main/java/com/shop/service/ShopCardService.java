package com.shop.service;

import com.shop.model.ShopCard;
import com.shop.repository.ShopCardRepository;
import com.shop.shared.classes.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
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
                Map<String, ShopCard> map = new HashMap<>();
                map.put("card", shopCard.get());
                response.setData(map);
            }
        } catch (Exception e) {
            response.setSuccess(false);
            response.setMessage(e.getMessage());
        }
        return ResponseEntity.ok(response);
    }
}
