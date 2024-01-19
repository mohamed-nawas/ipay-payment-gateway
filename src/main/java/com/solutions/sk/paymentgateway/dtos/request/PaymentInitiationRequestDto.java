package com.solutions.sk.paymentgateway.dtos.request;

import lombok.Getter;

@Getter
public class PaymentInitiationRequestDto implements RequestDto {

    private String orderId;

    @Override
    public String toLogJson() {
        return toJson();
    }

    @Override
    public boolean isRequiredAvailable() {
        return !orderId.isEmpty();
    }

}
