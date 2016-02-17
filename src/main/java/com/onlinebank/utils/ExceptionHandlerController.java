package com.onlinebank.utils;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.onlinebank.account.exceptions.AccountCreationFailedException;
import com.onlinebank.account.exceptions.AccountEditingException;
import com.onlinebank.account.exceptions.AccountNotFoundException;
import com.onlinebank.promotion.exceptions.PromotionNotFoundException;
import com.onlinebank.transaction.exceptions.TransactionNotFoundException;
import com.onlinebank.user.exceptions.UserEditingFailedException;
import com.onlinebank.user.exceptions.UserNotFoundException;
import com.onlinebank.user.exceptions.UserRegistrationFailedException;
import com.onlinebank.utils.exceptions.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Created by p0wontnx on 2/7/16.
 */
@ControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ObjectNode> userNotFoundHanlder() {

        ResponseBuilder responseBuilder = new ResponseBuilder();

        responseBuilder.setResponseErrors(new String[]{"User not found"});
        responseBuilder.setResponseStatus(HttpStatus.NOT_FOUND.getReasonPhrase());

        return new ResponseEntity<ObjectNode>(responseBuilder.build(), HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<ObjectNode> accountNotFoundHandler() {

        ResponseBuilder responseBuilder = new ResponseBuilder();

        responseBuilder.setResponseErrors(new String[]{"Account not found"});
        responseBuilder.setResponseStatus(HttpStatus.NOT_FOUND.getReasonPhrase());

        return new ResponseEntity<ObjectNode>(responseBuilder.build(), HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ObjectNode> badRequestExceptionHandler(BadRequestException e) {

        ResponseBuilder responseBuilder = new ResponseBuilder();

        responseBuilder.setResponseErrors(Utils.getModelNullFields(e.getRequest()));
        return new ResponseEntity<ObjectNode>(responseBuilder.setResponseStatus(HttpStatus.BAD_REQUEST.getReasonPhrase()).build(), HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(AccountCreationFailedException.class)
    public ResponseEntity<ObjectNode> accountCreationFailedHandler(AccountCreationFailedException e) {

        ResponseBuilder responseBuilder = new ResponseBuilder();

        responseBuilder.setResponseErrors(new String[]{"Account creation failed"});
        responseBuilder.setResponseStatus(HttpStatus.PRECONDITION_FAILED.getReasonPhrase());

        return new ResponseEntity<ObjectNode>(responseBuilder.build(), HttpStatus.PRECONDITION_FAILED);
    }

    @ExceptionHandler(AccountEditingException.class)
    public ResponseEntity<ObjectNode> accountEditingHandler() {

        ResponseBuilder responseBuilder = new ResponseBuilder();

        responseBuilder.setResponseErrors(new String[]{"Account editing failed"});
        responseBuilder.setResponseStatus(HttpStatus.PRECONDITION_FAILED.getReasonPhrase());

        return new ResponseEntity<ObjectNode>(responseBuilder.build(), HttpStatus.PRECONDITION_FAILED);

    }

    @ExceptionHandler(UserRegistrationFailedException.class)
    public ResponseEntity<ObjectNode> userRegistrationFailedHandler() {

        ResponseBuilder responseBuilder = new ResponseBuilder();

        responseBuilder.setResponseErrors(new String[]{"User registration failed"});
        responseBuilder.setResponseStatus(HttpStatus.PRECONDITION_FAILED.getReasonPhrase());

        return new ResponseEntity<ObjectNode>(responseBuilder.build(), HttpStatus.PRECONDITION_FAILED);

    }

    @ExceptionHandler(UserEditingFailedException.class)
    public ResponseEntity<ObjectNode> userEditingExceptionHandler() {

        ResponseBuilder responseBuilder = new ResponseBuilder();

        responseBuilder.setResponseErrors(new String[]{"User editing failed"});
        responseBuilder.setResponseStatus(HttpStatus.PRECONDITION_FAILED.getReasonPhrase());

        return new ResponseEntity<ObjectNode>(responseBuilder.build(), HttpStatus.PRECONDITION_FAILED);

    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<ObjectNode> bindExceptionHandler(BindException e) {

        ResponseBuilder responseBuilder = new ResponseBuilder();

        responseBuilder.setResponseErrors(new String[]{e.getFieldError().getField()});
        responseBuilder.setResponseStatus(HttpStatus.BAD_REQUEST.getReasonPhrase());

        return new ResponseEntity<ObjectNode>(responseBuilder.build(), HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(PromotionNotFoundException.class)
    public ResponseEntity<ObjectNode> promotionNotFoundHandler() {

        ResponseBuilder responseBuilder = new ResponseBuilder();

        responseBuilder.setResponseErrors(new String[]{"Promotion not found"});
        responseBuilder.setResponseStatus(HttpStatus.NOT_FOUND.getReasonPhrase());

        return new ResponseEntity<ObjectNode>(responseBuilder.build(), HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler(TransactionNotFoundException.class)
    public ResponseEntity<ObjectNode> transactionNotFoundExceptionHandler() {

        ResponseBuilder responseBuilder = new ResponseBuilder();

        responseBuilder.setResponseErrors(new String[]{"Transaction not found"});
        responseBuilder.setResponseStatus(HttpStatus.NOT_FOUND.getReasonPhrase());

        return new ResponseEntity<ObjectNode>(responseBuilder.build(), HttpStatus.NOT_FOUND);

    }

}
