package com.solutions.sk.paymentgateway.entities;

import java.util.UUID;

import com.solutions.sk.paymentgateway.dtos.request.PaymentNotificationRequestDto;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class PaymentDetails {

    @Transient
    private static final String PAYMENT_ID_PREFIX = "PID";

    @Id
    private String id;
    @Column(nullable = false)
    private String transactionReference;
    @Column(nullable = false)
    private String transactionAmount;
    @Column(nullable = false)
    private String creditedAmount;
    @Column(nullable = false)
    private String transactionStatus;
    @Column(nullable = false)
    private String transactionMessage;
    @Column(nullable = false)
    private String transactionTimeInMillis;
    @Column(nullable = false)
    private String checksum;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    private OrderDetails order;

    public PaymentDetails(PaymentNotificationRequestDto dto, OrderDetails order) {
        this.id = PAYMENT_ID_PREFIX + UUID.randomUUID();
        this.transactionReference = dto.getTransactionReference();
        this.order = order;
        this.transactionAmount = dto.getTransactionAmount();
        this.creditedAmount = dto.getCreditedAmount();
        this.transactionStatus = dto.getTransactionStatus();
        this.transactionMessage = dto.getTransactionMessage();
        this.transactionTimeInMillis = dto.getTransactionTimeInMillis();
        this.checksum = dto.getChecksum();
    }
}
