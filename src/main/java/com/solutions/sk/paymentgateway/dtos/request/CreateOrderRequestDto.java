package com.solutions.sk.paymentgateway.dtos.request;

import lombok.Getter;

@Getter
public class CreateOrderRequestDto implements RequestDto {

    private String name;
    private String phone;
    private String email;
    private String orderDescription;
    private String totalAmount;

    @Override
    public String toLogJson() {
        return toJson();
    }

    @Override
    public boolean isRequiredAvailable() {
        return !totalAmount.isEmpty();
    }

}
