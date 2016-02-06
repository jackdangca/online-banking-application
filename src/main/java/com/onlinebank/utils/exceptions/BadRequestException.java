package com.onlinebank.utils.exceptions;

/**
 * Created by p0wontnx on 1/27/16.
 */
public class BadRequestException extends Exception {

    private Object request;

    public BadRequestException(Object request) {
        this.request = request;
    }

    public Object getRequest() {
        return request;
    }

    public BadRequestException setRequest(Object request) {
        this.request = request;
        return this;
    }
}
