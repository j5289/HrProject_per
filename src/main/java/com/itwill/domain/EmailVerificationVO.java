package com.itwill.domain;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class EmailVerificationVO {

	private String empId;        // emp_id → empId
    private String email;
    private LocalDateTime verexpAt;  // verexp_at → verexpAt
    private boolean verified;
    private String unlockCode;     // unlock_code → unlockCode
    private LocalDateTime createdAt; // created_at → createdAt
    private LocalDateTime updatedAt; // updated_at → updatedAt
}
