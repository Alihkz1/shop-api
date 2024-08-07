package com.shop.controller;

import com.shop.command.OrderAddCommand;
import com.shop.command.OrderChangeStatusCommand;
import com.shop.command.OrderTrackCodeCommand;
import com.shop.query.OrderAdminListQuery;
import com.shop.query.OrderGetAllQuery;
import com.shop.service.OrderService;
import com.shop.shared.classes.Response;
import com.shop.shared.classes.UserThread;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("api/v1/order")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping(path = "add")
    public ResponseEntity<Response> add(@RequestBody OrderAddCommand command) {
        return orderService.add(command);
    }

    @GetMapping(path = "list")
    public ResponseEntity<Response> getAll(@ModelAttribute OrderGetAllQuery query) {
        return orderService.getAll(UserThread.getUserId(), query.getStatus());
    }

    @GetMapping(path = "track/{orderCode}")
    public ResponseEntity<Response> track(@PathVariable String orderCode) {
        return orderService.trackByOrderCode(orderCode);
    }

    @GetMapping(path = "admin-list")
    public ResponseEntity<Response> adminList(@ModelAttribute OrderAdminListQuery query) {
        return orderService.adminList(query.getStatus());
    }

    @PutMapping(path = "change-status")
    public ResponseEntity<Response> changeStatus(@RequestBody OrderChangeStatusCommand command) {
        return orderService.changeStatus(command);
    }

    @PutMapping(path = "track-code")
    public ResponseEntity<Response> trackCode(@RequestBody OrderTrackCodeCommand command) {
        return orderService.submitPostTrackCodeByAdmin(command);
    }
}
