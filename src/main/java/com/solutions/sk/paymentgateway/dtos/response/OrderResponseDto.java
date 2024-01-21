package com.solutions.sk.paymentgateway.dtos.response;

import com.solutions.sk.paymentgateway.entities.OrderDetails;

import lombok.Getter;

@Getter
public class OrderResponseDto implements ResponseDto {

    private String orderId;
    private String orderDescription;
    private String orderAmount;
    private String merchantWebToken;
    private String returnUrl;
    private String cancelUrl;

    @Override
    public String toLogJson() {
        return toJson();
    }

    public OrderResponseDto(OrderDetails order, String token, String returnUri, String cancelUri) {
        this.orderId = order.getId();
        this.orderDescription = order.getOrderDescription();
        this.orderAmount = order.getOrderAmount();
        this.merchantWebToken = token;
        this.returnUrl = returnUri + order.getId();
        this.cancelUrl = cancelUri + order.getId();
    }
}
