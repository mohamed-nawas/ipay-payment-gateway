package com.solutions.sk.paymentgateway.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.solutions.sk.paymentgateway.entities.OrderDetails;

@Repository
public interface OrderRepository extends JpaRepository<OrderDetails, String> {

}
