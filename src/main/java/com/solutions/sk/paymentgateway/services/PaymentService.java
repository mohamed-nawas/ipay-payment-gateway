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

    @Autowired
    public PaymentService(PaymentRepository paymentRepository) {
        this.repository = paymentRepository;
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
