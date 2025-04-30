package com.itwill.attendance.dto;

import java.sql.Date;
import java.sql.Timestamp;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 4. 관리자의 사원 근무 조회 및 근무 입력 DTO
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AttendanceAdminWorkDTO {
	
	private String attendanceId;
    private String empId; // 사원 ID
    private String empName; // 사원 이름
    private String depName; // 부서 이름

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date workDate; // 근무 날짜(단일 날짜)
    
    private Timestamp checkInTime;
    private Timestamp checkOutTime;

    private String workStatus;
    
    private int workDays; // 근무 일수
    private int workHours; // 근무 시간
    private int nightWorkHour; // 야근 시간
    private int holidayWorkHour; // 휴일 근무 시간
    private Date absenceDate; // 결근 날짜
    private Date businessTripStart; // 출장 시작일
    private Date businessTripEnd; // 출장 종료일
    private String tripLocation; // 출장지

    private String attendanceStatus; // 근무 상태 (예: 출근, 결근, 휴가, 출장)
    private int vacationDays; // 휴가 일수

}
