package com.solutions.sk.paymentgateway.wrappers;

import com.solutions.sk.paymentgateway.dtos.response.ResponseDto;
import com.solutions.sk.paymentgateway.enums.ResponseStatusType;

import lombok.Getter;

@Getter
public class ErrorResponseWrapper extends ResponseWrapper {

    private final int errorCode;

    public ErrorResponseWrapper(ResponseStatusType status, String message, ResponseDto data, 
                                String displayMessage, int errorCode) {
        super(status, message, data, displayMessage);
        this.errorCode = errorCode;
    }
}
