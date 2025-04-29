package com.itwill.approval.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class BusinessReqDTO {
    private String businessTripId;
    private String empId;
    private String tripLocation;
    private String businessTripPurpose;
    private LocalDate businessTripStart;
    private LocalDate businessTripEnd;
    private boolean approvalConfirmed = false;
}