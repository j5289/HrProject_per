package com.itwill.attendance.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Attendance {
    
    private String attendanceId;       // 출퇴근 기록 ID (예: 2504162024001att)
    private String employeeId;         // 사원 ID (예: 2504162024001)
    private LocalDate attendanceDate;  // 근태 날짜 (yyyy-mm-dd)
    private LocalDateTime arrivalTime; // 출근 시간 (yyyy-mm-dd HH:mm:ss)
    private LocalDateTime leaveTime;   // 퇴근 시간 (nullable, yyyy-mm-dd HH:mm:ss)
    private String status;             // 출근/지각/결근 등 상태 (예: "출근", "지각", "결근")
    private String lateReason;         // 지각 사유 (nullable, 예: "교통사고", "의료")
}
