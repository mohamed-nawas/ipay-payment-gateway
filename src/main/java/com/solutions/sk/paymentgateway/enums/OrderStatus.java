package com.solutions.sk.paymentgateway.enums;

import lombok.Getter;

@Getter
public enum OrderStatus {

    CREATED("Created"),
    COMPLETED("Completed");

    private final String status;

    OrderStatus(String status) {
        this.status = status;
    }
}
