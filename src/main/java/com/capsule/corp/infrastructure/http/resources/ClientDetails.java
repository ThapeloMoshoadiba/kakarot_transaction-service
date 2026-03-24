package com.capsule.corp.infrastructure.http.resources;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientDetails {

  String clientId;
  String createdAt;
  String updatedAt;
  String cifNumber;
  String title;
  String firstName;
  String middleName;
  String lastName;
  String idNumber;
  String gender;
  String dateOfBirth;
  String address;
  String cellphoneNumber;
  String email;
  String credit;
  String employmentStatus;
  String sourceOfFunds;
  String verifiedAnnualIncome;
}
