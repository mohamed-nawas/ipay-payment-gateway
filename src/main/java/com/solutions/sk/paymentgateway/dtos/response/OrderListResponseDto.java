package com.solutions.sk.paymentgateway.dtos.response;

import java.util.List;

import lombok.Getter;

@Getter
public class OrderListResponseDto implements ResponseDto {

    private final List<OrderResponseDto> orders;

    @Override
    public String toLogJson() {
        return toJson();
    }

    public OrderListResponseDto(List<OrderResponseDto> orders) {
        this.orders = orders;
    }
}
