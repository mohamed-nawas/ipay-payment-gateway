package com.solutions.sk.paymentgateway.entities;

import java.util.UUID;

import com.solutions.sk.paymentgateway.dtos.request.CreateOrderRequestDto;
import com.solutions.sk.paymentgateway.enums.OrderStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class OrderDetails {

    @Transient
    private static final String ORDER_ID_PREFIX = "OID";

    @Id
    private String id;
    @Column(nullable = false)
    private String orderAmount;
    @Column(nullable = true)
    private String orderDescription;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @Column(nullable = true)
    private String orderedPersonName;
    @Column(nullable = true)
    private String orderedPersonPhone;
    @Column(nullable = true)
    private String orderedPersonEmail;

    @OneToOne(mappedBy = "order")
    private PaymentDetails payment;

    public OrderDetails(CreateOrderRequestDto dto) {
        this.id = ORDER_ID_PREFIX + UUID.randomUUID();
        this.orderAmount = dto.getTotalAmount();
        this.orderDescription = dto.getOrderDescription();
        this.orderStatus = OrderStatus.CREATED;
        this.orderedPersonName = dto.getName();
        this.orderedPersonPhone = dto.getPhone();
        this.orderedPersonEmail = dto.getEmail();
    }
}
