package com.capsule.corp.domain.service;

import com.capsule.corp.domain.persistance.TransactionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class InterestService {
  private final TransactionRepository transactionRepository;

  public void calculateInterest() {}
}
