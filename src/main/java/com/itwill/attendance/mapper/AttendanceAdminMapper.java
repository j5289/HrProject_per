package com.itwill.attendance.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.itwill.attendance.dto.AttendanceAdminCheckDTO;
import com.itwill.attendance.dto.AttendanceAdminLeaveDTO;

public interface AttendanceAdminMapper {

	// ===== 1. 관리자의 사원 출퇴근 기록 및 현황 조회 =====
	// 1) 사원 출퇴근 기록 및 현황 조회(이름, 날짜 등 조건 필터링)
	 List<AttendanceAdminCheckDTO> selectAdminAttendanceList(Map<String, Object> params);

	// 2) 특정 사원의 특정 날짜 근무 기록 단건 조회
	 AttendanceAdminCheckDTO selectAdminAttendanceByEmpIdAndDate(Map<String, Object> params);

	 // ===== 2. 관리자의 사원 휴가 목록 조회 =====
	 List<AttendanceAdminLeaveDTO> selectLeaveByEmployeeForAdmin(
		        @Param("startDate") String startDate,
		        @Param("endDate") String endDate
		    );



}
