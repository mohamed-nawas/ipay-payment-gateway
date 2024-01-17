package com.solutions.sk.paymentgateway.dtos.request;

import lombok.Getter;

@Getter
public class PaymentNotificationRequestDto implements RequestDto {

    private String transactionReference;
    private String orderId;
    private String transactionAmount;
    private String creditedAmount;
    private String transactionStatus;
    private String transactionMessage;
    private String transactionTimeInMillis;
    private String checksum;

    @Override
    public String toLogJson() {
        return toJson();
    }

    @Override
    public boolean isRequiredAvailable() {
        return !transactionReference.isEmpty() && !orderId.isEmpty() && !transactionAmount.isEmpty() &&
                !creditedAmount.isEmpty() && !transactionStatus.isEmpty() && !transactionMessage.isEmpty() &&
                !transactionTimeInMillis.isEmpty() && !checksum.isEmpty();
    }

}
