package com.shop.command;

import com.shop.shared.enums.OrderStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderChangeStatusCommand {
    private Long orderId;
    private OrderStatus orderStatus;
}
