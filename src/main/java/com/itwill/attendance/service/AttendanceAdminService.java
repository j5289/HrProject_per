package com.itwill.attendance.service;

import java.util.List;
import java.util.Map;

import com.itwill.attendance.dto.AttendanceAdminCheckDTO;

public interface AttendanceAdminService {

	//===== 1. 관리자용 사원의 출퇴근 기록부 및 현황 조회 =====
	//통합 조건 기반 전체 조회
	 List<AttendanceAdminCheckDTO> getAdminAttendanceList(Map<String, Object> params);

	//단건 조회(상세 보기용)
	 AttendanceAdminCheckDTO getAdminAttendanceDetail(Map<String, Object> params);
	 
}
