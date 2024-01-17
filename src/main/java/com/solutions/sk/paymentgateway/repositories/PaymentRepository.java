package com.solutions.sk.paymentgateway.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.solutions.sk.paymentgateway.entities.PaymentDetails;

@Repository
public interface PaymentRepository extends JpaRepository<PaymentDetails, String> {

}
