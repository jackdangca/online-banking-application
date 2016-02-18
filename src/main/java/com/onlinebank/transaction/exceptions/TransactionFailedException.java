package com.onlinebank.transaction.exceptions;

/**
 * Created by p0wontnx on 2/17/16.
 */
public class TransactionFailedException extends Exception {

    public TransactionFailedException() {

    }

    public TransactionFailedException(String message) {
        super(message);
    }

}
