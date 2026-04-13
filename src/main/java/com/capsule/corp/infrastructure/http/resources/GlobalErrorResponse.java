package com.capsule.corp.infrastructure.http.resources;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GlobalErrorResponse {

  @Builder.Default private boolean success = false;
  private String reason;
}
