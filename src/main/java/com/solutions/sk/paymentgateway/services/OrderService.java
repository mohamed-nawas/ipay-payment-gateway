package com.solutions.sk.paymentgateway.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.solutions.sk.paymentgateway.dtos.request.PaymentNotificationRequestDto;
import com.solutions.sk.paymentgateway.entities.OrderDetails;
import com.solutions.sk.paymentgateway.exceptions.SkSolutionsException;
import com.solutions.sk.paymentgateway.repositories.OrderRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class OrderService {

    private final OrderRepository repository;

    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.repository = orderRepository;
    }

    /**
     * This method creates an Order in DB
     * 
     * @param order order details
     * @return Order details
     */
    public OrderDetails createOrder(OrderDetails order) {
        try {
            return repository.save(order);
        } catch (DataAccessException e) {
            throw new SkSolutionsException("Error occurred when saving order to DB for orderId: " + order.getId(), e);
        }
    }

    public boolean verifyOrderPayment(PaymentNotificationRequestDto paymentDetails) {
        try {
            OrderDetails order = getOrder(paymentDetails.getOrderId());
            if (Integer.parseInt(order.getOrderAmount()) != Integer.parseInt(paymentDetails.getTransactionAmount())) {
                throw new SkSolutionsException("Order amount does not match the transaction amount for payment" +
                        " reference: " + paymentDetails.getTransactionReference());
            }
            return true;
        } catch (EntityNotFoundException e) {
            throw new SkSolutionsException("Order with id: " + paymentDetails.getOrderId() + " not found in DB", e);
        } catch (DataAccessException e) {
            throw new SkSolutionsException("Error occurred when verifying order payment", e);
        }
    }

    public OrderDetails getOrder(String orderId) {
        try {
            return repository.getReferenceById(orderId);
        } catch (EntityNotFoundException e) {
            throw new SkSolutionsException("Order with id: " + orderId + " not found in DB", e);
        } catch (DataAccessException e) {
            throw new SkSolutionsException("Error occurred when getting order from DB", e);
        }
    }
}
