package com.solutions.sk.paymentgateway.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.solutions.sk.paymentgateway.dtos.request.PaymentInitiationRequestDto;
import com.solutions.sk.paymentgateway.dtos.request.PaymentNotificationRequestDto;
import com.solutions.sk.paymentgateway.dtos.response.PaymentResponseDto;
import com.solutions.sk.paymentgateway.entities.OrderDetails;
import com.solutions.sk.paymentgateway.entities.PaymentDetails;
import com.solutions.sk.paymentgateway.enums.ErrorResponseStatusType;
import com.solutions.sk.paymentgateway.enums.SuccessResponseStatusType;
import com.solutions.sk.paymentgateway.exceptions.SkSolutionsException;
import com.solutions.sk.paymentgateway.services.OrderService;
import com.solutions.sk.paymentgateway.services.PaymentService;
import com.solutions.sk.paymentgateway.wrappers.ResponseWrapper;

import lombok.extern.slf4j.Slf4j;

/**
 * Payment controller
 * API definitions
 */
@RestController
@Slf4j
@RequestMapping("/api/v1/payments")
public class PaymentController extends Controller {

    private final PaymentService service;
    private final OrderService orderService;

    @Autowired
    public PaymentController(PaymentService paymentService, OrderService orderService) {
        this.service = paymentService;
        this.orderService = orderService;
    }

    @PostMapping(path = "/init", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseWrapper> initiatePayment(@RequestBody PaymentInitiationRequestDto requestDto) {
        try {
            if (!requestDto.isRequiredAvailable())
                return getBadRequestResponse(ErrorResponseStatusType.MISSING_REQUIRED_FIELDS);

            OrderDetails order = orderService.getOrder(requestDto.getOrderId());
            service.initiatePayment(order.getId(), order.getOrderDescription());
            ;

            log.debug("Successfully initiated the payment for orderId: {}", requestDto.getOrderId());
            return getSuccessResponse(SuccessResponseStatusType.INITIATE_PAYMENT, null);
        } catch (SkSolutionsException e) {
            log.error("Error occurred during initiating the payment: {}", e);
            return getInternalServerErrorResponse();
        }
    }

    @PostMapping(path = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseWrapper> capturePayment(@RequestBody PaymentNotificationRequestDto requestDto) {
        try {
            if (!requestDto.isRequiredAvailable())
                return getBadRequestResponse(ErrorResponseStatusType.MISSING_REQUIRED_FIELDS);

            if (!orderService.verifyOrderPayment(requestDto)) {
                log.error("Order payment cannot be verified for payment: {}", requestDto.toLogJson());
                return getBadRequestResponse(ErrorResponseStatusType.PAYMENT_UNVERIFIED);
            }
            PaymentDetails payment = new PaymentDetails(requestDto, orderService.getOrder(requestDto.getOrderId()));
            PaymentDetails savedPayment = service.createPayment(payment);
            PaymentResponseDto responseDto = new PaymentResponseDto(savedPayment);

            log.debug("Successfully captured the payment: {}", responseDto.toLogJson());
            return getSuccessResponse(SuccessResponseStatusType.CAPTURE_PAYMENT, responseDto);
        } catch (SkSolutionsException e) {
            log.error("Error occurred during capturing the payment: {}", e);
            return getInternalServerErrorResponse();
        }
    }
}
