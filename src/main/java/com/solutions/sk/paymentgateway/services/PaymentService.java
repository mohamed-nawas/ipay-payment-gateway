package com.solutions.sk.paymentgateway.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.solutions.sk.paymentgateway.entities.PaymentDetails;
import com.solutions.sk.paymentgateway.exceptions.SkSolutionsException;
import com.solutions.sk.paymentgateway.repositories.PaymentRepository;

@Service
public class PaymentService {

    private final PaymentRepository repository;
    private final IPGGatewayService gatewayService;

    @Autowired
    public PaymentService(PaymentRepository paymentRepository, IPGGatewayService ipgGatewayService) {
        this.repository = paymentRepository;
        this.gatewayService = ipgGatewayService;
    }

    /**
     * This method is used to initiate a payment for an order
     * 
     * @param orderId          order id
     * @param orderDescription order description
     */
    public void initiatePayment(String orderId, String orderDescription) {
        try {
            gatewayService.checkOut(orderId, orderDescription);
        } catch (Exception e) {
            throw new SkSolutionsException("Error occurred when initiating the payment for orderId: " + orderId);
        }
    }

    /**
     * This method creates a Payment in DB
     * 
     * @param payment payment details
     * @return Payment details
     */
    public PaymentDetails createPayment(PaymentDetails payment) {
        try {
            return repository.save(payment);
        } catch (DataAccessException e) {
            throw new SkSolutionsException("Error occurred when saving payment to DB for payment: " + payment.getId(),
                    e);
        }
    }
}
