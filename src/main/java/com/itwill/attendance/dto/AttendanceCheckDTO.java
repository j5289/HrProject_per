package com.itwill.attendance.dto;

import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 1. 사원의 출퇴근 기록부 및 현황 DTO
 *
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AttendanceCheckDTO {
	
	private String empId; //사원 Id
	private String empName; //사원 이름
	private String workDate; //근무 날짜(단일 날짜)
	private String checkInTime; //출근 시간
	private String checkOutTime; //퇴근 시간 
	
	private Boolean isLate; //지각 여부(DB에 없음, true 면 지각)
	private LocalTime nowTime; //현재 시각(DB에 없음, 출근/퇴근 버튼 누를 때 표시용) 
	
	
}
