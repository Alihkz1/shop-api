package com.shop.service;

import com.shop.command.OrderAddCommand;
import com.shop.config.JWTService;
import com.shop.model.Order;
import com.shop.model.Product;
import com.shop.repository.OrderRepository;
import com.shop.shared.classes.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final ShopCardService shopCardService;
    private final JWTService jwtService;

    public OrderService(OrderRepository repository, ShopCardService shopCardService, JWTService jwtService) {
        this.orderRepository = repository;
        this.shopCardService = shopCardService;
        this.jwtService = jwtService;
    }

    public ResponseEntity<Response> add(OrderAddCommand command) {
        Response response = new Response();
        try {
            shopCardService.shopCardIsPaid(command.getShopCardId());
            orderRepository.save(command.toEntity());
        } catch (Exception e) {
            response.setMessage(e.getMessage());
            response.setSuccess(false);
        }
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<Response> getAll(Long userId) {
        Response response = new Response();
        try {
            Map<String, List<Order>> map = new HashMap<>();
//            Map<String, Object> tokenDetails = (Map<String, Object>) SecurityContextHolder.getContext().getAuthentication().getDetails();
            Optional<List<Order>> userOrders = orderRepository.findByUserId(userId);
            map.put("orders", userOrders.get());
            response.setData(map);
        } catch (Exception e) {
            response.setMessage(e.getMessage());
            response.setSuccess(false);
        }
        return ResponseEntity.ok(response);
    }
}
