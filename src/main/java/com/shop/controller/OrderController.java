package com.shop.controller;

import com.shop.command.OrderAddCommand;
import com.shop.service.OrderService;
import com.shop.shared.classes.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("api/v1/order")
@CrossOrigin(origins = "*")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping(path = "add")
    public ResponseEntity<Response> add(@RequestBody OrderAddCommand command) {
        return orderService.add(command);
    }

    @GetMapping(path = "list/{userId}")
    public ResponseEntity<Response> getAll(@PathVariable Long userId) {
        return orderService.getAll(userId);
    }
}
