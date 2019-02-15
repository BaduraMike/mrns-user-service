package com.soft.mikessolutions.userservice.advices.address;

import com.soft.mikessolutions.userservice.exceptions.address.AddressAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class AddressFoundAdvice {
    @ResponseBody
    @ExceptionHandler(AddressAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.FOUND)
    String AddressFoundHandler(AddressAlreadyExistsException ex) {
        return ex.getMessage();
    }

}
