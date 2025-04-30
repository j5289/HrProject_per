package com.itwill.approval.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class LeaveReqDTO {
    private String leaveId;
    private String empId;
    private String leaveStatus;
    private LocalDate leaveStartDate;
    private LocalDate leaveEndDate;
    private int leaveDays;
    private boolean approvalConfirmed = false;
}