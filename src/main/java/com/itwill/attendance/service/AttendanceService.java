package com.itwill.attendance.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import com.itwill.attendance.dto.AttendanceCheckDTO;
import com.itwill.attendance.dto.AttendanceLateDTO;

public interface AttendanceService {

	// ===== 1. 출퇴근 기록부 및 현황 =====
	// 1) 출근 처리
    AttendanceCheckDTO checkIn(String empId);

    // 2) 퇴근 처리
    AttendanceCheckDTO checkOut(String empId);

    // 3) 특정 사원의 출퇴근 기록 조회 (날짜 기준)
    AttendanceCheckDTO getAttendanceByEmpIdAndDate(String empId, LocalDate workDate);
    
    // ===== 2. 사용자 지각 현황 =====
    // 1) 기간 내 지각 상세 내역 조회 (지각 발생 날짜, 사유, 사유서 정보 포함)
    List<AttendanceLateDTO> getLateDetailsByEmpIdAndDateRange(Map<String, Object> paramMap);

    // 2) 기간 내 지각 통계 조회 (총 지각 횟수, 총 지각 시간)
    AttendanceLateDTO getLateStatsByEmpIdAndDateRange(Map<String, Object> paramMap);
}
