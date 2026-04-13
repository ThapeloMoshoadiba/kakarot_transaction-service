package com.capsule.corp.common.exception;

import com.capsule.corp.infrastructure.http.resources.GlobalErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.DataException;
import org.postgresql.util.PSQLException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler({AccountNotFoundException.class, BalanceNotFoundException.class, TransactionsNotFoundException.class})
  public ResponseEntity<?> handleNotFoundExceptions(final Exception ex) {
    log.error("NotFoundException:", ex);
    return ResponseEntity.status(HttpStatus.NO_CONTENT)
        .body(GlobalErrorResponse.builder().reason(ex.getMessage()).build());
  }

  @ExceptionHandler({HttpMessageNotReadableException.class, MethodArgumentNotValidException.class})
  public ResponseEntity<?> handleBadRequestException(final Exception ex) {
    log.error("BadRequestException:", ex);
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(GlobalErrorResponse.builder().reason("Bad Request").build());
  }

  @ExceptionHandler({
    DataException.class,
    PSQLException.class,
    DataIntegrityViolationException.class
  })
  public ResponseEntity<?> handleDatabaseException(final Exception ex) {
    log.error("DatabaseException:", ex);
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(GlobalErrorResponse.builder().reason("Database Error").build());
  }

  @ExceptionHandler(BusinessRuleException.class)
  public ResponseEntity<?> handleBusinessRuleException(final BusinessRuleException ex) {
    log.error("BusinessRuleException:", ex);
    return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
        .body(GlobalErrorResponse.builder().reason(ex.getMessage()).build());
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<?> handleGenericException(final Exception ex) {
    log.error("Exception:", ex);
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(GlobalErrorResponse.builder().reason(ex.getMessage()).build());
  }
}
