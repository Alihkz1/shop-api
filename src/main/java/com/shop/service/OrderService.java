package com.shop.service;

import com.shop.command.OrderAddCommand;
import com.shop.command.OrderChangeStatusCommand;
import com.shop.dto.OrderDto;
import com.shop.dto.OrderListDto;
import com.shop.dto.OrderProductDto;
import com.shop.model.Order;
import com.shop.model.Product;
import com.shop.model.ProductSize;
import com.shop.model.ShopCard;
import com.shop.repository.OrderRepository;
import com.shop.repository.ProductRepository;
import com.shop.repository.ProductSizeRepository;
import com.shop.repository.ShopCardRepository;
import com.shop.shared.classes.Response;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final ShopCardService shopCardService;

    private final ShopCardRepository shopCardRepository;

    private final ProductRepository productRepository;
    private final ProductSizeRepository sizeRepository;
    private final Environment environment;

    public OrderService(OrderRepository repository, ShopCardService shopCardService, ProductRepository productRepository,
                        Environment environment, ShopCardRepository shopCardRepository, ProductSizeRepository sizeRepository) {
        this.orderRepository = repository;
        this.shopCardService = shopCardService;
        this.productRepository = productRepository;
        this.environment = environment;
        this.shopCardRepository = shopCardRepository;
        this.sizeRepository = sizeRepository;
    }

    public ResponseEntity<Response> add(OrderAddCommand command) {
        /*todo: increase products buyCount*/
        Response response = new Response();
        try {
            Order order = orderRepository.save(command.toEntity());
            shopCardService.payShopCards(order.getOrderId(), order.getUserId());
            decreaseProductsAmount(order.getOrderId());
            increaseProductsBuyCount(order.getOrderId());
            smsAdminForNewOrder();
        } catch (Exception e) {
            response.setMessage(e.getMessage());
            response.setSuccess(false);
        }

        response.setData(orderRepository.getOrderCodeByShopCardId(command.getShopCardId()));
        return ResponseEntity.ok(response);
    }

    private void smsAdminForNewOrder() {
//        try {
//            KavenegarApi api = new KavenegarApi(environment.getProperty("kavehNegarApiKey"));
//            SendResult result = api.send(
//                    environment.getProperty("kavehNegarSender"),
//                    environment.getProperty("adminPhoneNumber"),
//                    "Hi Kiwi"
//            );
//        } catch (HttpException ex) {
//            System.out.print("HttpException  : " + ex.getMessage());
//        } catch (ApiException ex) {
//            System.out.print("ApiException : " + ex.getMessage());
//        }
    }

    public ResponseEntity<Response> getAll(Long userId, Byte status) {
        Response response = new Response();
        List<OrderDto> userAllOrders = new ArrayList<>();
        try {
            Optional<List<OrderListDto>> userOrders;
            Map<String, List<OrderDto>> map = new HashMap<>();
            if (status == null) userOrders = orderRepository.findByUserId(userId);
            else userOrders = orderRepository.findByUserId(userId, status);
            userOrders.get().forEach(userOrder -> {
                OrderDto orderDto = new OrderDto();
                List<OrderProductDto> products = new ArrayList<>();
                Optional<List<ShopCard>> orderShopCards = shopCardRepository.findByOrderId(userOrder.getOrderId());
                orderShopCards.get().forEach(shopCard -> {
                    OrderProductDto productDto = new OrderProductDto();
                    productDto.setProduct(productRepository.findByProductId(shopCard.getProductId()).get());
                    productDto.setSize(shopCard.getSize());
                    productDto.setAmount(shopCard.getAmount());
                    products.add(productDto);
                });
                orderDto.setProducts(products);
                orderDto.setOrder(userOrder);
                userAllOrders.add(orderDto);
            });
            map.put("userAllOrders", userAllOrders);
            response.setData(map);
        } catch (Exception e) {
            response.setMessage(e.getMessage());
            response.setSuccess(false);
        }
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<Response> track(String orderCode) {
        Response response = new Response();
        Optional<OrderListDto> order = orderRepository.findByCode(orderCode);
        if (order.isEmpty()) {
            response.setMessage("wrong orderCode!");
        } else {
            Map<String, OrderDto> map = new HashMap<>();
            OrderDto orderDto = new OrderDto();
            List<OrderProductDto> products = new ArrayList<>();
            orderDto.setOrder(order.get());
            Long orderId = orderRepository.getOrderIdByOrderCode(orderCode);
            Optional<List<ShopCard>> orderShopCards = shopCardRepository.findByOrderId(orderId);
            orderShopCards.get().forEach(shopCard -> {
                OrderProductDto productDto = new OrderProductDto();
                productDto.setProduct(productRepository.findByProductId(shopCard.getProductId()).get());
                productDto.setSize(shopCard.getSize());
                productDto.setAmount(shopCard.getAmount());
                products.add(productDto);
            });
            orderDto.setProducts(products);
            map.put("order", orderDto);
            response.setData(map);
        }
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<Response> adminList(Byte status) {
        Response response = new Response();
        try {
            Optional<List<OrderListDto>> usersOrders;
            Map<String, List<OrderDto>> map = new HashMap<>();
            List<OrderDto> allOrders = new ArrayList<>();

            if (status != null) usersOrders = orderRepository.adminList(status);
            else usersOrders = orderRepository.adminList();

            usersOrders.get().forEach(userOrder -> {
                OrderDto orderDto = new OrderDto();
                List<OrderProductDto> products = new ArrayList<>();
                Optional<List<ShopCard>> orderShopCards = shopCardRepository.findByOrderId(userOrder.getOrderId());
                orderShopCards.get().forEach(shopCard -> {
                    OrderProductDto productDto = new OrderProductDto();
                    productDto.setProduct(productRepository.findByProductId(shopCard.getProductId()).get());
                    productDto.setSize(shopCard.getSize());
                    productDto.setAmount(shopCard.getAmount());
                    products.add(productDto);
                });
                orderDto.setProducts(products);
                orderDto.setOrder(userOrder);
                allOrders.add(orderDto);
            });
            map.put("allOrders", allOrders);
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

    private void decreaseProductsAmount(Long orderId) {
        List<ShopCard> orderShopCards = shopCardRepository.findByOrderId(orderId).get();
        orderShopCards.forEach(shopCard -> {
            Product product = productRepository.findByProductId(shopCard.getProductId()).get();
            if (shopCard.getSize() != null) {
                sizeRepository.reduceAmountByProductIdAndSize(product.getProductId(), shopCard.getSize(), shopCard.getAmount());
            } else {
                productRepository.reduceProductAmount(product.getProductId(), shopCard.getAmount());
            }
        });
    }

    private void increaseProductsBuyCount(Long orderId) {
        List<ShopCard> orderShopCards = shopCardRepository.findByOrderId(orderId).get();
        orderShopCards.forEach(shopCard -> {
            Product product = productRepository.findByProductId(shopCard.getProductId()).get();
            productRepository.increaseProductBuyCount(product.getProductId(), shopCard.getAmount());
        });
    }
}
