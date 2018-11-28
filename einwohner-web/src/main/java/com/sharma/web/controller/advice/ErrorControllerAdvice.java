package com.sharma.web.controller.advice;

import com.sharma.data.resource.ErrorCode;
import com.sharma.data.resource.ErrorInfo;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ErrorControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ErrorInfo handleValidationExceptions(MethodArgumentNotValidException exception) {
        return ErrorInfo.builder().errorCode(ErrorCode.BAD_REQUEST).errorMessage(exception.getMessage()).build();
    }
}
