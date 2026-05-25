package com.babak.stock.web;

import com.babak.stock.exception.StockNotFoundException;
import com.babak.stock.web.model.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.Instant;

@ControllerAdvice
@ResponseBody
public class ControllersAdvice extends ResponseEntityExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(ControllersAdvice.class);

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ErrorResponse handleMethodArgumentTypeMismatch(HttpServletRequest request,
                                                          MethodArgumentTypeMismatchException ex) {
        String message = ex.getMostSpecificCause() != null ? ex.getMostSpecificCause().getMessage() : ex.getMessage();
        return build(request, ex, HttpStatus.BAD_REQUEST, message, "INV-INP");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public ErrorResponse handleIllegalArgument(HttpServletRequest request, IllegalArgumentException ex) {
        return build(request, ex, HttpStatus.BAD_REQUEST, ex.getMessage(), "INV-INP");
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(StockNotFoundException.class)
    public ErrorResponse handleStockNotFound(HttpServletRequest request, StockNotFoundException ex) {
        return build(request, ex, HttpStatus.NOT_FOUND, ex.getMessage(), "INV-ID");
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ErrorResponse handleUnexpected(HttpServletRequest request, Exception ex) {
        log.error("Unexpected error", ex);
        return build(request, ex, HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), "INT-ERR");
    }

    private ErrorResponse build(HttpServletRequest request, Exception ex, HttpStatus status, String message, String code) {
        String path = request.getRequestURI() +
                (request.getQueryString() != null ? "?" + request.getQueryString() : "");
        ErrorResponse response = ErrorResponse.builder()
                .timestamp(Instant.now())
                .status(status.value())
                .error(status.getReasonPhrase())
                .code(code)
                .exception(ex.getClass().getSimpleName())
                .message(message)
                .path(path)
                .build();
        log.error("{}", response, ex);
        return response;
    }
}
