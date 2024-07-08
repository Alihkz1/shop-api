package com.shop.controller;

import com.shop.command.OrderAddCommand;
import com.shop.command.OrderChangeStatusCommand;
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
    public ResponseEntity<Response> getAll(@PathVariable Long userId, @RequestParam(value = "status", required = false) Byte status) {
        return orderService.getAll(userId, status);
    }

    @GetMapping(path = "track/{orderCode}")
    public ResponseEntity<Response> track(@PathVariable String orderCode) {
        return orderService.track(orderCode);
    }

    @GetMapping(path = "admin-list")
    public ResponseEntity<Response> adminList(@RequestParam(value = "status", required = false) Byte status) {
        return orderService.adminList(status);
    }

    @PutMapping(path = "change-status")
    public ResponseEntity<Response> changeStatus(@RequestBody OrderChangeStatusCommand command) {
        return orderService.changeStatus(command);
    }
}
