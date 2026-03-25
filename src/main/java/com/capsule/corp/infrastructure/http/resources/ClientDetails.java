package com.capsule.corp.infrastructure.http.resources;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientDetails {

  UUID clientId;
  LocalDateTime createdAt;
  LocalDateTime updatedAt;
  String cifNumber;

  String title; // might need the title enum
  String firstName;
  String middleName;
  String lastName;
  String idNumber;
  String gender; // might need the gender enum
  LocalDate dateOfBirth;
  String address;
  String cellphoneNumber;
  String email;

  String credit; // might need the credit enum
  String employmentStatus; // might need the employmentStatus enum
  String sourceOfFunds; // might need the sourceOfFunds enum
  BigDecimal verifiedAnnualIncome;

  String clientStatus; // might need the clientStatus enum

  LocalDateTime blockedAt;
  String reasonForBlock;

  LocalDateTime unblockedAt;
  String reasonForUnblock;
}
