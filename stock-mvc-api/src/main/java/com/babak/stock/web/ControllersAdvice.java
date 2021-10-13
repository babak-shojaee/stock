package com.babak.stock.web;

import com.babak.stock.exception.StockNotFoundException;
import com.babak.stock.web.model.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.server.ServerWebInputException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;
import java.util.Optional;
import java.util.function.BiConsumer;

@ControllerAdvice
@Slf4j
public class ControllersAdvice  extends ResponseEntityExceptionHandler {


    private static final BiConsumer<Exception, ErrorResponse> ERROR_LOGGER = (originalException, errorResponse) ->
            log.error(errorResponse.toString(), originalException);

    private static final BiConsumer<Exception, ErrorResponse> WARN_LOGGER = (originalException, errorResponse) ->
            log.error(errorResponse.toString(), originalException);


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = MethodArgumentTypeMismatchException.class)
    public @ResponseBody
    ErrorResponse handleMethodArgumentTypeMismatchException(ServerHttpRequest request,
                                                            MethodArgumentTypeMismatchException ex) {
        final var message = ex.getMostSpecificCause().getMessage();
        return createErrorResponse(request, ex, HttpStatus.BAD_REQUEST, message, "INV-INP", ERROR_LOGGER);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = IllegalArgumentException.class)
    public @ResponseBody
    ErrorResponse handleIllegalArgumentException(ServerHttpRequest request,
                                                 IllegalArgumentException ex) {
        return createErrorResponse(request, ex, HttpStatus.BAD_REQUEST, ex.getMessage(), "INV-INP", ERROR_LOGGER);
    }



    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = Exception.class)
    public @ResponseBody
    ErrorResponse handleUnexpectedException(ServerHttpRequest request, Exception ex) {
        return createErrorResponse(request, ex, HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), "INT-ERR", ERROR_LOGGER);
    }



    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(value = StockNotFoundException.class)
    public @ResponseBody
    ErrorResponse stockNotFound(ServerHttpRequest request, StockNotFoundException ex) {
        return createErrorResponse(request, ex, HttpStatus.NOT_FOUND, ex.getMessage(), "INV-ID", ERROR_LOGGER);
    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = WebExchangeBindException.class)
    public @ResponseBody
    ErrorResponse handleWebExchangeBindException(ServerHttpRequest request, WebExchangeBindException ex) {

        return createErrorResponse(request, ex, HttpStatus.BAD_REQUEST, ex.getMessage(), "INV-ID", ERROR_LOGGER);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = ServerWebInputException.class)
    public @ResponseBody
    ErrorResponse handleWebExchangeBindException(ServerHttpRequest request, ServerWebInputException ex) {

        return createErrorResponse(request, ex, HttpStatus.BAD_REQUEST, ex.getMessage(), "INV-ID", ERROR_LOGGER);
    }


    private ErrorResponse  createErrorResponse(ServerHttpRequest request,
                                              Exception ex,
                                              HttpStatus httpStatus,
                                              String message,
                                              String errorCode,
                                              BiConsumer<Exception, ErrorResponse> loggerConsumer) {

        final var errorResponse = ErrorResponse.builder()
                .timestamp(new Date())
                .status(httpStatus.value())
                .error(httpStatus.getReasonPhrase())
                .code(errorCode)
                .exception(ex.getClass().getSimpleName())
                .message(message)
                .path(request.getURI() + Optional.ofNullable(request.getQueryParams()).map(qs -> "?" + qs).orElse(""))
                .build();

        loggerConsumer.accept(ex, errorResponse);
        return errorResponse;
    }
}
