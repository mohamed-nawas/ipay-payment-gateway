package com.solutions.sk.paymentgateway.dtos.response;

import com.solutions.sk.paymentgateway.entities.PaymentDetails;

import lombok.Getter;

@Getter
public class PaymentResponseDto implements ResponseDto {

    private String orderId;
    private String orderDescription;
    private String transactionReference;
    private String transactionAmount;
    private String transactionStatus;
    private String transactionMessage;

    @Override
    public String toLogJson() {
        return toJson();
    }

    public PaymentResponseDto(PaymentDetails payment) {
        this.orderId = payment.getOrder().getId();
        this.orderDescription = payment.getOrder().getOrderDescription();
        this.transactionReference = payment.getTransactionReference();
        this.transactionAmount = payment.getTransactionAmount();
        this.transactionStatus = payment.getTransactionStatus();
        this.transactionMessage = payment.getTransactionMessage();
    }
}
