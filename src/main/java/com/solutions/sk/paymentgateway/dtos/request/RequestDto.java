package com.solutions.sk.paymentgateway.dtos.request;

import com.solutions.sk.paymentgateway.dtos.BaseDto;

public interface RequestDto extends BaseDto {

    /**
     * This method verifies if all required fields exists for a request
     *
     * @return true/false
     */
    boolean isRequiredAvailable();
}
