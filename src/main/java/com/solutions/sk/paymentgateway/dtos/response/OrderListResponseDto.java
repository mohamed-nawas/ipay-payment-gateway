package com.solutions.sk.paymentgateway.dtos.response;

import java.util.List;

import com.solutions.sk.paymentgateway.entities.OrderDetails;

import lombok.Getter;

@Getter
public class OrderListResponseDto implements ResponseDto {

    private final List<OrderDetails> orders;

    @Override
    public String toLogJson() {
        return toJson();
    }

    public OrderListResponseDto(List<OrderDetails> orders) {
        this.orders = orders;
    }
}
