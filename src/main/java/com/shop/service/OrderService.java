package com.shop.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shop.command.OrderAddCommand;
import com.shop.command.OrderChangeStatusCommand;
import com.shop.dto.OrderListDto;
import com.shop.dto.ShopCardDto;
import com.shop.model.Order;
import com.shop.repository.OrderRepository;
import com.shop.repository.ProductRepository;
import com.shop.shared.classes.Response;
import com.shop.shared.enums.OrderStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final ShopCardService shopCardService;
    private final ProductRepository productRepository;

    public OrderService(OrderRepository repository, ShopCardService shopCardService, ProductRepository productRepository) {
        this.orderRepository = repository;
        this.shopCardService = shopCardService;
        this.productRepository = productRepository;
    }

    public ResponseEntity<Response> add(OrderAddCommand command) {
        Response response = new Response();
        try {
            shopCardService.shopCardIsPaid(command.getShopCardId());
            orderRepository.save(command.toEntity());
            changeProductsAmount(command.getShopCardId());
        } catch (Exception e) {
            response.setMessage(e.getMessage());
            response.setSuccess(false);
        }
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<Response> getAll(Long userId, Byte status) {
        Response response = new Response();
        try {
            Optional<List<OrderListDto>> userOrders = Optional.empty();
            Map<String, List<OrderListDto>> map = new HashMap<>();
            if (status == null) userOrders = orderRepository.findByUserId(userId);
            else userOrders = orderRepository.findByUserId(userId, status);
            map.put("userAllOrders", userOrders.get());
            response.setData(map);
        } catch (Exception e) {
            response.setMessage(e.getMessage());
            response.setSuccess(false);
        }
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<Response> adminList(Byte status) {
        Response response = new Response();
        try {
            Optional<List<OrderListDto>> userOrders = Optional.empty();
            Map<String, List<OrderListDto>> map = new HashMap<>();
            if (status != null) userOrders = orderRepository.adminList(status);
            else userOrders = orderRepository.adminList();
            map.put("allOrders", userOrders.get());
            response.setData(map);
        } catch (Exception e) {
            response.setMessage(e.getMessage());
            response.setSuccess(false);
        }
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<Response> changeStatus(OrderChangeStatusCommand command) {
        Response response = new Response();
        Optional<Order> order = orderRepository.findByOrderId(command.getOrderId());
        try {
            if (order.isPresent()) {
                order.get().setStatus(command.getOrderStatus());
                orderRepository.save(order.get());
            } else {
                response.setSuccess(false);
                response.setMessage("wrong orderId!");
            }
        } catch (Exception e) {
            response.setSuccess(false);
            response.setMessage(e.getMessage());
        }
        return ResponseEntity.ok(response);
    }

    private void changeProductsAmount(Long shopCardId) {
        ObjectMapper objectMapper = new ObjectMapper();
        String stringProducts = orderRepository.getOrderProductsByShopCardId(shopCardId);
        if (!stringProducts.isEmpty()) {
            try {

                List<ShopCardDto> products = objectMapper.readValue(stringProducts, new TypeReference<List<ShopCardDto>>() {
                });

                products.forEach(product -> {
                    productRepository.reduceProductAmount(product.getProductId(), product.getInCardAmount());
                });


            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
