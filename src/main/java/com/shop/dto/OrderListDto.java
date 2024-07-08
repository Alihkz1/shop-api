package com.shop.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

public interface OrderListDto {

    @JsonProperty("orderId")
    Long getOrderId();

    @JsonProperty("shopCardId")
    Long getShopCardId();

    @JsonProperty("userId")
    Long getUserId();

    @JsonProperty("receiverName")
    String getReceiverName();

    @JsonProperty("receiverPhone")
    String getReceiverPhone();

    @JsonProperty("receiverEmail")
    String getReceiverEmail();

    @JsonProperty("postalCode")
    Long getPostalCode();

    @JsonProperty("status")
    Byte getStatus();

    @JsonProperty("address")
    String getAddress();

    @JsonProperty("description")
    String getDescription();

    @JsonProperty("date")
    Long getDate();

    @JsonProperty("products")
    String getProducts();

    @JsonProperty("paid")
    Byte getPaid();

    @JsonProperty("code")
    String getCode();
}
