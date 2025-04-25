package com.itwill.attendance.dto;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 관리자의 수정/삭제 dto
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AttendanceAdminUpdateAndDeleteDTO {

	  private String empId; // 사원 id
	    private Date workDate; // 근무 날짜
	    private int workHours; // 근무 시간
	    private int nightWorkHour; // 야근 시간
	    private int holidayWorkHour; // 휴일 근무 시간
	    private String attendanceStatus; // 근무 상태
	    private int vacationDays; // 휴가 일수
	
	    public boolean updateWorkStatus(AttendanceAdminUpdateAndDeleteDTO dto) {
	        // DTO를 사용하여 근무 상태 수정 로직 구현
	        // 예: 데이터베이스 업데이트 로직
	        return true; // 수정 성공 시 true 반환
	    }

	    public boolean deleteWorkStatus(AttendanceAdminUpdateAndDeleteDTO dto) {
	        // DTO를 사용하여 근무 상태 삭제 로직 구현
	        // 예: 데이터베이스 삭제 로직
	        return true; // 삭제 성공 시 true 반환
	    }

	    
}
