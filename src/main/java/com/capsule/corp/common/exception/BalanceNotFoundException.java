package com.capsule.corp.common.exception;

public class BalanceNotFoundException extends RuntimeException {
  public BalanceNotFoundException(final String message) {
    super(message);
  }
}
