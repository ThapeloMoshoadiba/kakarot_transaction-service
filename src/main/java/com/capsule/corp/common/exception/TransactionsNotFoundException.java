package com.capsule.corp.common.exception;

public class TransactionsNotFoundException extends RuntimeException {
  public TransactionsNotFoundException(final String message) {
    super(message);
  }
}
