package com.shop.service;

import com.shop.command.OrderAddCommand;
import com.shop.command.OrderChangeStatusCommand;
import com.shop.command.OrderTrackCodeCommand;
import com.shop.dto.*;
import com.shop.model.Order;
import com.shop.model.Product;
import com.shop.model.ShopCard;
import com.shop.repository.*;
import com.shop.shared.Exceptions.BadRequestException;
import com.shop.shared.classes.BaseService;
import com.shop.shared.classes.Response;
import com.shop.shared.enums.ErrorMessagesEnum;
import com.shop.shared.enums.OrderStatus;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class OrderService extends BaseService {
    private final OrderRepository orderRepository;
    private final ShopCardService shopCardService;
    private final ShopCardRepository shopCardRepository;
    private final ProductRepository productRepository;
    private final ProductSizeRepository sizeRepository;
    private final UserRepository userRepository;
    private final Environment environment;

    public OrderService(
            Environment environment,
            OrderRepository repository,
            ShopCardService shopCardService,
            ProductRepository productRepository,
            ShopCardRepository shopCardRepository,
            ProductSizeRepository sizeRepository,
            UserRepository userRepository
    ) {
        this.orderRepository = repository;
        this.shopCardService = shopCardService;
        this.productRepository = productRepository;
        this.environment = environment;
        this.shopCardRepository = shopCardRepository;
        this.sizeRepository = sizeRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public ResponseEntity<Response> add(OrderAddCommand command) {
        Order order = orderRepository.save(command.toEntity());
        shopCardService.payShopCards(order.getOrderId(), order.getUserId());
        modifyProductsAfterOrder(order.getOrderId());
        updateUserOrdersByUserId(order.getUserId(), order.getOrderId());
        smsAdminForNewOrder();
        return successResponse();
    }

    public ResponseEntity<Response> getAll(Long userId, Byte status) {
        List<OrderDto> userAllOrders = new ArrayList<>();
        Optional<List<OrderList>> userOrders = status == null ? orderRepository.findByUserId(userId) : orderRepository.findByUserId(userId, status);
        userOrders.ifPresent(orders -> orders.forEach(userOrder -> {
            OrderDto orderDto = new OrderDto();
            List<OrderProductDto> products = new ArrayList<>();
            Optional<List<ShopCard>> orderShopCards = shopCardRepository.findByOrderId(userOrder.getOrderId());
            orderShopCards.ifPresent(shopCards -> shopCards.forEach(shopCard -> {
                OrderProductDto productDto = new OrderProductDto();
                Optional<Product> product = productRepository.findByProductId(shopCard.getProductId());
                product.ifPresent(p -> {
                    productDto.setProduct(p);
                    productDto.setSize(shopCard.getSize());
                    productDto.setAmount(shopCard.getAmount());
                    products.add(productDto);
                });
            }));
            orderDto.setProducts(products);
            orderDto.setOrder(userOrder);
            userAllOrders.add(orderDto);
        }));
        return successResponse(new OrdersDto(userAllOrders));
    }

    public ResponseEntity<Response> adminList(Byte status) {
        List<OrderDto> allOrders = new ArrayList<>();
        Optional<List<OrderList>> usersOrders = status != null ? orderRepository.adminList(status) : orderRepository.adminList();
        usersOrders.ifPresent(orders -> orders.forEach(userOrder -> {
            OrderDto orderDto = new OrderDto();
            List<OrderProductDto> products = new ArrayList<>();
            Optional<List<ShopCard>> orderShopCards = shopCardRepository.findByOrderId(userOrder.getOrderId());
            orderShopCards.ifPresent(shopCards -> shopCards.forEach(shopCard -> {
                OrderProductDto productDto = new OrderProductDto();
                Optional<Product> product = productRepository.findByProductId(shopCard.getProductId());
                product.ifPresent(p -> {
                    productDto.setProduct(p);
                    productDto.setSize(shopCard.getSize());
                    productDto.setAmount(shopCard.getAmount());
                    products.add(productDto);
                });
            }));
            orderDto.setProducts(products);
            orderDto.setOrder(userOrder);
            allOrders.add(orderDto);
        }));
        return successResponse(new OrdersDto(allOrders));
    }

    public ResponseEntity<Response> trackByOrderCode(String orderCode) {
        Optional<OrderList> order = orderRepository.findByCode(orderCode);
        if (order.isEmpty()) {
            throw new BadRequestException(ErrorMessagesEnum.NO_ORDER_FOUND.getMessage());
        } else {
            OrderDto orderDto = new OrderDto();
            List<OrderProductDto> products = new ArrayList<>();
            orderDto.setOrder(order.get());
            Long orderId = orderRepository.getOrderIdByOrderCode(orderCode);
            Optional<List<ShopCard>> orderShopCards = shopCardRepository.findByOrderId(orderId);
            orderShopCards.ifPresent(shopCards -> shopCards.forEach(shopCard -> {
                OrderProductDto productDto = new OrderProductDto();
                Optional<Product> product = productRepository.findByProductId(shopCard.getProductId());
                product.ifPresent(p -> {
                    productDto.setProduct(p);
                    productDto.setSize(shopCard.getSize());
                    productDto.setAmount(shopCard.getAmount());
                    products.add(productDto);
                });
            }));
            orderDto.setProducts(products);
            return successResponse(new OrderRetrieveDto(orderDto));
        }
    }

    public ResponseEntity<Response> submitPostTrackCodeByAdmin(OrderTrackCodeCommand command) {
        Optional<Order> order = orderRepository.findByOrderId(command.getOrderId());
        if (order.isEmpty()) {
            throw new BadRequestException(ErrorMessagesEnum.NO_ORDER_FOUND.getMessage());
        } else {
            order.get().setTrackCode(command.getTrackCode());
            order.get().setStatus(OrderStatus.SENT_VIA_POST);
            orderRepository.save(order.get());
            return successResponse();
        }
    }

    public ResponseEntity<Response> changeStatus(OrderChangeStatusCommand command) {
        Optional<Order> order = orderRepository.findByOrderId(command.getOrderId());
        if (order.isPresent()) {
            order.get().setStatus(command.getOrderStatus());
            orderRepository.save(order.get());
            return successResponse();
        } else {
            throw new BadRequestException(ErrorMessagesEnum.NO_ORDER_FOUND.getMessage());
        }
    }

    @Transactional
    protected void modifyProductsAfterOrder(Long orderId) {
        List<ShopCard> orderShopCards = shopCardRepository.findByOrderId(orderId).orElse(Collections.emptyList());
        orderShopCards.forEach(shopCard -> {
            Optional<Product> productOptional = productRepository.findByProductId(shopCard.getProductId());
            productOptional.ifPresent(product -> {
                productRepository.increaseProductBuyCount(product.getProductId(), shopCard.getAmount());
                if (shopCard.getSize() != null) {
                    sizeRepository.reduceAmountByProductIdAndSize(product.getProductId(), shopCard.getSize(), shopCard.getAmount());
                } else {
                    productRepository.decreaseProductAmount(product.getProductId(), shopCard.getAmount());
                }
            });
        });
    }

    private void updateUserOrdersByUserId(Long userId, Long orderId) {
        AtomicReference<Long> newOrderPrice = new AtomicReference<>(0L);
        List<CardProductIdAmount> productsOInfo = shopCardRepository.findProductIdAndAmountByOrderId(orderId);
        productsOInfo.forEach(product -> newOrderPrice.updateAndGet(v -> v + product.getPrice() * product.getAmount()));
        userRepository.updateUserOrdersByUserId(userId, newOrderPrice.get());
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
}
