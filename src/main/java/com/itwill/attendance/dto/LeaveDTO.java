package com.itwill.attendance.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LeaveDTO {
    private String leaveId;
    private String empId;
    private String requestDate;
    private String startDate;
    private String endDate;
    private String reportFile;     // 파일명
    private String approvalStatus; // 승인 상태
}
