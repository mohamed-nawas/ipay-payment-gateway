package com.solutions.sk.paymentgateway.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.solutions.sk.paymentgateway.dtos.request.CreateOrderRequestDto;
import com.solutions.sk.paymentgateway.dtos.response.OrderListResponseDto;
import com.solutions.sk.paymentgateway.dtos.response.OrderResponseDto;
import com.solutions.sk.paymentgateway.entities.OrderDetails;
import com.solutions.sk.paymentgateway.enums.ErrorResponseStatusType;
import com.solutions.sk.paymentgateway.enums.SuccessResponseStatusType;
import com.solutions.sk.paymentgateway.exceptions.SkSolutionsException;
import com.solutions.sk.paymentgateway.services.OrderService;
import com.solutions.sk.paymentgateway.wrappers.ResponseWrapper;

import lombok.extern.slf4j.Slf4j;

/**
 * Order controller
 * API definitions
 */
@RestController
@Slf4j
@RequestMapping("/api/v1/orders")
public class OrderController extends Controller {

    private final OrderService service;
    private final String token;
    private final String returnUri;
    private final String cancelUri;

    @Autowired
    public OrderController(OrderService orderService,
            @Value("${payment.ipg.token}") String token,
            @Value("${payment.ipg.redirect-uri.return}") String returnUri,
            @Value("${payment.ipg.redirect-uri.cancel}") String cancelUri) {
        this.service = orderService;
        this.token = token;
        this.returnUri = returnUri;
        this.cancelUri = cancelUri;
    }

    @PostMapping(path = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseWrapper> createOrder(@RequestBody CreateOrderRequestDto requestDto) {
        try {
            if (!requestDto.isRequiredAvailable())
                return getBadRequestResponse(ErrorResponseStatusType.MISSING_REQUIRED_FIELDS);

            OrderDetails order = new OrderDetails(requestDto);
            OrderDetails savedOrder = service.createOrder(order);
            OrderResponseDto responseDto = new OrderResponseDto(savedOrder, token, returnUri, cancelUri);

            log.debug("Successfully created the order: {}", responseDto.toLogJson());
            return getSuccessResponse(SuccessResponseStatusType.CREATE_ORDER, responseDto);
        } catch (SkSolutionsException e) {
            log.error("Error occurred during creating the order: {}", e);
            return getInternalServerErrorResponse();
        }
    }

    @GetMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseWrapper> getOrders() {
        try {
            var orderDetails = service.getOrders();
            var orders = orderDetails.stream().map(order -> new OrderResponseDto(order, token, returnUri, cancelUri)).toList();
            OrderListResponseDto responseDto = new OrderListResponseDto(orders);

            log.debug("Successfully returned all the orders, {}", responseDto.toLogJson());
            return getSuccessResponse(SuccessResponseStatusType.GET_ORDERS, responseDto);
        } catch (SkSolutionsException e) {
            log.error("Error occurred while getting all orders, {}", e);
            return getInternalServerErrorResponse();
        }
    }

    @DeleteMapping(path = "/{orderId}", produces = MediaType.APPLICATION_JSON_VALUE )
    public ResponseEntity<ResponseWrapper> deleteOrderByOrderId(@PathVariable(name = "orderId") String orderId) {
        try {
            service.deleteOrder(orderId);

            log.debug("Successfully deleted the order with orderId: , {}", orderId);
            return getSuccessResponse(SuccessResponseStatusType.DELETE_ORDER, null);
        } catch (SkSolutionsException e) {
            log.error("Error occurred while deleting order, orderId: {}", orderId, e);
            return getInternalServerErrorResponse();
        }
    }
}
