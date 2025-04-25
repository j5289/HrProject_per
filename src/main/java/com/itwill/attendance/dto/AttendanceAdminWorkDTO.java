package com.itwill.attendance.dto;

import java.sql.Date;

import lombok.Data;

/**
 * 4. 관리자의 사원 근무 조회 및 근무 입력 DTO
 *
 */

@Data
public class AttendanceAdminWorkDTO {
	
	private String empId; // 사원 id
    private String empName; // 사원이름
    private String depName; // 부서 이름
    
    private Date workDate; // 근무 날짜(단일 날짜)
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
