package com.shop.service;

import com.shop.command.OrderAddCommand;
import com.shop.config.JWTService;
import com.shop.dto.OrderListDto;
import com.shop.repository.OrderRepository;
import com.shop.shared.classes.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final ShopCardService shopCardService;

    public OrderService(OrderRepository repository, ShopCardService shopCardService) {
        this.orderRepository = repository;
        this.shopCardService = shopCardService;
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
            Map<String, List<OrderListDto>> map = new HashMap<>();
            Optional<List<OrderListDto>> userOrders = orderRepository.findByUserId(userId);
            map.put("userAllOrders", userOrders.get());
            response.setData(map);
        } catch (Exception e) {
            response.setMessage(e.getMessage());
            response.setSuccess(false);
        }
        return ResponseEntity.ok(response);
    }
}
