package com.itwill.attendance.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 3. 사원의 근무 조회 DTO
 *
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AttendanceWorkCheckDTO {
	
	private String empId; //사원 번호
	private String empName; //사원 이름 

	private Integer totalTime; //누적 근무 시간 (DB에 없음)
	private Integer totalDate; //누적 근무 일수 (DB에 없음)
	
	//조회 기간 
	private LocalDate startDate; //조회 시작 날짜 (DB에 없음)
	private LocalDate endDate; //조회 종료 날짜 (DB에 없음)
	
	private String workDays; //일반 근무 일수 
	private String workHours; //일반 근무 시간 
	private Integer latenessMinutes; //지각 시간, 해당 데이터가 있는 날짜의 근무 일수에서 해당 데이터 값만큼 뺀 값이 근무 시간.
	//만약 2024년 05월 12일에 latenessMinutes 가 존재한다면 해당 지각 시간만큼 근무 시간에서 빼서 누적 근무 시간 계산 
	
	private String category; //근무 유형 필터 (ex. 일반근무, 야근, 출장, 휴일 근무 등), DB에 없음
	
	private String absenceId; //결근 Id, 결근한 경우 해당 날짜는 근무시간과 근무일수에 포함되지 않음
	
	
	private Integer nightWorkHour; //야근 시간, 야근 여부 확인과 시간에 사용
	private Integer hoildayWorkHour; //휴일 근무 시간, 휴일 근무 여부 확인과 시간에 사용
	
	private String businessTripId; //출장 Id, 출장 여부에 사용
	private LocalDate businessTripStart; //출장 시작일, 출장 근무 일수 계산에 사용
	private LocalDate businessTripEnd; //출장 종료일, 출장 근무 일수 계산에 사용
	//출장 근무 일수 = 출장 종료일 - 출장 시작일 
	private String leaveDays; //휴가일수, 해당 정보 있으면 근무 일수에서 휴가 일수만큼 제외하기

	private Integer actualWorkDays; // 휴가 제외 실제 근무일 수(DB에 없음)

	
}
