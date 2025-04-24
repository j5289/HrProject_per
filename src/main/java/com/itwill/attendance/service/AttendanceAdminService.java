package com.itwill.attendance.service;

import java.util.List;

import com.itwill.attendance.dto.AttendanceAdminCheckDTO;

public interface AttendanceAdminService {

	//===== 1. 관리자용 사원의 출퇴근 기록부 및 현황 조회 =====
	// 전체 사원의 근무 기록 조회 
	List<AttendanceAdminCheckDTO> getAllEmployeeAttendance();
	
	//특정 사원의 근무 기록 조회 
	List<AttendanceAdminCheckDTO> getEmployeeAttendanceByEmpId(String empId);
	
	//특정 날짜의 전체 사원 근무 기록 조회 
	List<AttendanceAdminCheckDTO> getAttendanceByDate(String workDate);
	
	
}
