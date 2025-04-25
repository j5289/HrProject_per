package com.itwill.attendance.service;

import java.util.List;
import java.util.Map;

import com.itwill.attendance.dto.AttendanceAdminCheckDTO;
import com.itwill.attendance.dto.AttendanceAdminLeaveDTO;

public interface AttendanceAdminService {

	//===== 1. 관리자용 사원의 출퇴근 기록부 및 현황 조회 =====
	// 관리자의 사원 출퇴근 기록 및 현황 조회
    List<AttendanceAdminCheckDTO> getAdminAttendanceList(Map<String, Object> params);

    // 관리자의 특정 사원의 특정 날짜 근무 기록 조회
    AttendanceAdminCheckDTO getAdminAttendanceDetail(Map<String, Object> params);

    // ===== 2. 관리자용 사원 휴가 목록 조회 =====
    List<AttendanceAdminLeaveDTO> getLeaveListByAdmin(String empId, String empName);
    
}
