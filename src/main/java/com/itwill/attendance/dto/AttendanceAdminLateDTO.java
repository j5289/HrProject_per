package com.itwill.attendance.dto;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import lombok.Data;

/**
 * 3.관리자의 사원별 지각 현황 조회 DTO
 *
 */

@Data
public class AttendanceAdminLateDTO {
	
	 private String empId; // 사원 ID
	    private String empName; // 사원이름 
	    private String managerId; // 관리자 ID, DB에 없음
	    
	    private Date workDate; // 근무 날짜(단일 날짜)
	    private Timestamp checkInTime; // 출근 시간
	    
	    private Boolean isLate; // 지각 여부(DB에 없음, true 면 지각)
	    private int latenessCount; // 지각 횟수
	    private List<Date> latenessDates; // 지각 날짜 목록 (여러 날짜를 저장할 수 있도록 List<Date>로 변경)
	    
	    // 지각 여부 출력하는 메서드 
	    public String getLateStatus() {
	        if (isLate != null && isLate) {
	            return "예";
	        } else {
	            return "아니오";
	        }
	    }

	    // 지각 횟수 계산 (latenessCount와 latenessDates를 동기화)
	    public void calculateLatenessCount() {
	        if (latenessDates != null) {
	            this.latenessCount = latenessDates.size();
	        }
	    }
	}