package com.wookie.lukapp.exception;

import java.util.Date;

public class ErrorDetails {
    private Date date;
    private String exceptionCode;
    private String details;

    public ErrorDetails(Date date, String exceptionCode, String details) {
        this.date = date;
        this.exceptionCode = exceptionCode;
        this.details = details;
    }

    public Date getDate() {
        return date;
    }

    public String getExceptionCode() {
        return exceptionCode;
    }

    public String getDetails() {
        return details;
    }
}
