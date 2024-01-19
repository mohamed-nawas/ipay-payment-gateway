package com.solutions.sk.paymentgateway.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.solutions.sk.paymentgateway.dtos.request.IPGInitiatePaymentRequestDto;
import com.solutions.sk.paymentgateway.exceptions.SkSolutionsException;

import lombok.extern.slf4j.Slf4j;

/**
 * This class handles request to IPG gateway service
 */
@Service
@Slf4j
public class IPGGatewayService {

    private final String ipgCheckoutUrl;
    private final String merchantWebToken;
    private final String returnUri;
    private final String cancelUri;
    private final RestTemplate restTemplate;

    public IPGGatewayService(@Value("${payment.ipg.url}") String ipgCheckoutUrl,
            @Value("${payment.ipg.token}") String merchantWebToken,
            @Value("${payment.ipg.redirect-uri.return}") String returnUri,
            @Value("${payment.ipg.redirect-uri.cancel}") String cancelUri,
            RestTemplate restTemplate) {
        this.ipgCheckoutUrl = ipgCheckoutUrl;
        this.merchantWebToken = merchantWebToken;
        this.returnUri = returnUri;
        this.cancelUri = cancelUri;
        this.restTemplate = restTemplate;
    }

    /**
     * Calls IPG Gateway service to initiate the payment
     *
     * @param orderId          order id
     * @param orderDescription order description
     */
    public void checkOut(String orderId, String orderDescription) {
        IPGInitiatePaymentRequestDto dto = new IPGInitiatePaymentRequestDto(merchantWebToken, orderId, orderDescription,
                returnUri + orderId, cancelUri + orderId);
        HttpEntity<IPGInitiatePaymentRequestDto> httpEntity = new HttpEntity<>(dto, getHttpHeaders());

        try {
            log.info("Calling IPG Payment gateway server to initiate payment for orderId: {}", orderId);
            var result = restTemplate.exchange(ipgCheckoutUrl, HttpMethod.POST, httpEntity, Void.class);
            if (result.getStatusCode() != HttpStatus.OK) {
                log.error("Initiating payment for orderId was not successful from IPG Payment gateway server");
                throw new SkSolutionsException(
                        "Initiating payment for orderId was not successful from IPG Payment gateway server");
            }
            log.info("Request IPG Payment gateway server to initiate payment for orderId: {} is successful", orderId);
        } catch (HttpClientErrorException e) {
            log.error("Checkingout with IPG Payment gateway failed, e: {}", e);
            throw new SkSolutionsException("Checkingout with IPG Payment gateway failed", e);
        }
    }

    private HttpHeaders getHttpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }
}
