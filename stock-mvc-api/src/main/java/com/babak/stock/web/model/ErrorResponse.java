package com.babak.stock.web.model;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Builder
@Data
public class ErrorResponse implements Serializable {
    private Date timestamp;
    private int status;
    private String error;
    private String code;
    private String exception;
    private String message;
    private String path;
}
