package com.shop.command;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderTrackCodeCommand {
    private Long orderId;
    private String trackCode;
}
