package com.onlinebank.utils;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * Created by p0wontnx on 1/31/16.
 */
public class ResponseBuilder {

    private Object responseResult = "";
    private Object responseErrors = "";
    private Object responseStatus = "";

    public ResponseBuilder() {
    }

    public Object getResponseResult() {
        return responseResult;
    }

    public ResponseBuilder setResponseResult(Object responseResult) {
        this.responseResult = responseResult;
        return this;
    }

    public Object getResponseErrors() {
        return responseErrors;
    }

    public ResponseBuilder setResponseErrors(Object responseErrors) {
        this.responseErrors = responseErrors;
        return this;
    }

    public Object getResponseStatus() {
        return responseStatus;
    }

    public ResponseBuilder setResponseStatus(Object responseStatus) {
        this.responseStatus = responseStatus;
        return this;
    }

    public ObjectNode build() {

        ObjectNode objectNode = new ObjectNode(JsonNodeFactory.instance);

        // assign response result
        objectNode.putPOJO("result", responseResult);

        // assign reponse errors
        objectNode.putPOJO("errors", responseErrors);

        // assign response status
        objectNode.putPOJO("status", responseStatus);

        return objectNode;
    }
}
