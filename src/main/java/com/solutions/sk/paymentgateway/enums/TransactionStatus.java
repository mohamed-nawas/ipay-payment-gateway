package com.solutions.sk.paymentgateway.enums;

import lombok.Getter;

/**
 * All kinds of transaction status
 * supported by iPay
 */
@Getter
public enum TransactionStatus {

    A("Accepted", "Transaction process is successfully completed"),
    P("Pending", "Money successfully deducted from the customer bank account, but unable to transfer to merchants account. The amount will be transferred at the End of Day"),
    D("Decline", "Transaction failed");
    
    private final String status;
    private final String description;

    TransactionStatus(String status, String description) {
        this.status = status;
        this.description = description;
    }
}
