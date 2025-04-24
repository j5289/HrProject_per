package com.itwill.attendance.mapper;

import java.util.List;
import java.util.Map;

import com.itwill.attendance.dto.AttendanceAdminCheckDTO;

public interface AttendanceAdminMapper {

	// ===== 1. 관리자의 사원 출퇴근 기록 및 현황 조회 =====
	// 1) 전체 사원의 특정 날짜 또는 기간 출퇴근 기록 조회 
	List<AttendanceAdminCheckDTO> selectAdminAttendanceList(Map<String, Object> params);

	// 2) 특정 사원의 날짜별 출근/퇴근/지각 여부 단건 조회 
	 AttendanceAdminCheckDTO selectAdminAttendanceByEmpIdAndDate(Map<String, Object> params);




}
