package com.solutions.sk.paymentgateway.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class IPGInitiatePaymentRequestDto {

    private String merchantWebToken;
    private String orderId;
    private String orderDescription;
    private String returnUrl;
    private String cancelUrl;
}
