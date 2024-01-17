package com.solutions.sk.paymentgateway.wrappers;

import com.solutions.sk.paymentgateway.dtos.BaseDto;
import com.solutions.sk.paymentgateway.dtos.response.ResponseDto;
import com.solutions.sk.paymentgateway.enums.ResponseStatusType;

import lombok.Getter;

@Getter
public class ResponseWrapper implements BaseDto {

    private final ResponseStatusType status;
    private final String message;
    private final ResponseDto data;
    private final String displayMessage;

    public ResponseWrapper(ResponseStatusType status, String message, ResponseDto data, 
                           String displayMessage) {
        this.status = status;
        this.message = message;
        this.data = data;
        this.displayMessage = displayMessage;
    }

    @Override
    public String toLogJson() {
        return toJson();
    }
    
}
