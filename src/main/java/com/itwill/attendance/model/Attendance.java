package com.itwill.attendance.model;

import lombok.Builder;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * ATTENDANCE 테이블의 레코드를 표현하는 모델 클래스입니다.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Attendance {

    private String attendanceId;       // 예: 2504162024001att
    private String employeeId;         // 사원 ID
    private LocalDate attendanceDate;  // 근태 날짜
    private LocalDateTime arrivalTime; // 출근 시간
    private LocalDateTime leaveTime;   // 퇴근 시간 (nullable)
    private String status;             // 출근/지각/결근 등

}

