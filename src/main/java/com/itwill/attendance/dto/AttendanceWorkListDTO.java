package com.itwill.attendance.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 4. 사원의 근태 항목 조회 DTO
 *
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AttendanceWorkListDTO {
	
	private String empId; //사원 번호
	private LocalDate workDate; //조회 날짜(단일) : 해당 날짜의 근무 형태가 궁금한 경우 사용 
	
	//조회 기간 
	private LocalDate startDate; //조회 시작 날짜 (DB에 없음)
	private LocalDate endDate; //조회 종료 날짜 (DB에 없음)
	
	private String categoryAtt; //근태 유형 필터(DB에 없음), 근무,지각,결근,휴가,출장,야근,휴일근무
	
	private String absenceId; //결근 Id,날짜에 해당 정보 있다면 결근 처리
	private Integer nightWorkHour; //야근 시간, 해당 정보 있다면 야근 처리
	private Integer holidayWorkHour; //휴일 근무 시간, 해당 정보 있다면 휴일 근무 처리
	private String businessTripId; //출장 Id, 해당 정보 있다면 출장 처리
	private String latenessMinutes; //지각 시간, 해당 정보 있다면 지각 처리
	private String leaveId; //휴가Id, 해당 정보 있다면 휴가 처리
	private String latenessDates; //지각 날짜
	private int latenessCounts; //지각 횟수
	private LocalTime checkInTime; //출근 시간
	private LocalTime checkOutTime; //퇴근 시간 
	
	private String workStatus; //근태 상태 요약(최종 출력용 근무 상태)(근무,지각,결근,출장) DB에 없음
	
	
}
