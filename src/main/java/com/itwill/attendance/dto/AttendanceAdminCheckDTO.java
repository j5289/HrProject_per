package com.itwill.attendance.dto;

import java.sql.Date;
import java.sql.Timestamp;

import lombok.Data;

/**
 * 1. 관리자의 사원 출퇴근 기록부 및 현황 조회
 *
 */

@Data
public class AttendanceAdminCheckDTO {
	
	private String empId; //사원 id
	private String empName; //사원이름 
	private String managerId; //관리자 id, DB에 없음
	
	private Date workDate; //근무 날짜(단일 날짜)
	private Timestamp checkInTime; //출근 시간
	private Timestamp checkOutTime; //퇴근 시간 
	
	private Boolean isLate; //지각 여부(DB에 없음, true 면 지각)
	
	private String workStatus; //근무 상태(출근,퇴근,지각) 
	private String workTime; //근무 시간
	
	//지각 여부 출력하는 메서드 
	public String getLateStatus() {
		if(isLate != null && isLate) {
			return "예";
		}else {
			return "아니오";
		}
	}
	
//work_time 필드 추가
	
	//attendance_status는 db에 없음... 필드가 db에서 정확하게 일치하는지?!..
}
