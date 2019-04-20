package com.rohan90.majdoor.api.common;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import javax.persistence.EntityNotFoundException;

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice()
public class RestExceptionHandler {

    private ResponseEntity<RestResponse<Object>> buildResponse(ApiError apiError) {
        return new ResponseEntity<>(new RestResponse<>(apiError), apiError.getStatus());
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<RestResponse<Object>> handleAllExceptions(Exception ex, WebRequest request) {
        ex.printStackTrace();
        return buildResponse(new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, "Something broke!", ex));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public final ResponseEntity<RestResponse<Object>> handleMethodArumentNotValid(MethodArgumentNotValidException ex, WebRequest request) {
        BindingResult result = ex.getBindingResult();
        String firstFieldErrorMessage = result.getFieldErrors().get(0).getDefaultMessage();
        return buildResponse(new ApiError(HttpStatus.BAD_REQUEST, firstFieldErrorMessage, ex));
    }

//    @ExceptionHandler(value = UnauthorizedException.class)
//    protected ResponseEntity<RestResponse<Object>> handleUnauthorized(UnauthorizedException ex) {
//        return buildResponse(new ApiError(HttpStatus.UNAUTHORIZED, ex.getMessage(), ex));
//    }

    @ExceptionHandler(value = EntityNotFoundException.class)
    protected ResponseEntity<RestResponse<Object>> handleNotFound(EntityNotFoundException ex) {
        return buildResponse(new ApiError(HttpStatus.NOT_FOUND, ex.getMessage(), ex));
    }

}
