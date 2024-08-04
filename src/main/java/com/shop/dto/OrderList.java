package com.shop.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public interface OrderList {

    @JsonProperty("orderId")
    Long getOrderId();

    @JsonProperty("username")
    String getUsername();

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

    @JsonProperty("trackCode")
    String getTrackCode();

    @JsonProperty("description")
    String getDescription();

    @JsonProperty("date")
    Long getDate();

    @JsonProperty("paidAmount")
    Long getPaidAmount();

    @JsonProperty("code")
    String getCode();
}
