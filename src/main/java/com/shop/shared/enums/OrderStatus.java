package com.shop.shared.enums;

public enum OrderStatus {
    PAID((byte) 0),
    SENT_VIA_POST((byte) 1),
    DELIVERED((byte) 2),
    NOT_DELIVERED((byte) 3);

    OrderStatus(Byte status) {
    }
}
