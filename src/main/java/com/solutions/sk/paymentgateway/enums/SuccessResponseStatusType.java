package com.solutions.sk.paymentgateway.enums;

import lombok.Getter;

@Getter
public enum SuccessResponseStatusType {

    CREATE_ORDER(200, "Successfully created the order"),
    INITIATE_PAYMENT(200, "Successfully initiated the payment"),
    CAPTURE_PAYMENT(200, "Successfully captured the payment"),
    GET_ORDERS(200, "Successfully returned the orders"),
    DELETE_ORDER(200, "Successfully deleted the order");

    private final int code;
    private final String message;

    SuccessResponseStatusType(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
