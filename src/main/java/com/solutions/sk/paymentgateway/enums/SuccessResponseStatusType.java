package com.solutions.sk.paymentgateway.enums;

import lombok.Getter;

@Getter
public enum SuccessResponseStatusType {

    CREATE_ORDER(200, "Successfully created the order"),
    CAPTURE_PAYMENT(200, "Successfully captured the payment");

    private final int code;
    private final String message;

    SuccessResponseStatusType(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
