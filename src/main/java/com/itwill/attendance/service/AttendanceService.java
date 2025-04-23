package com.itwill.attendance.service;

import java.time.LocalDate;
import java.util.List;
import com.itwill.attendance.dto.AttendanceCheckDTO;

public interface AttendanceService {

	// ===== 1. 출퇴근 기록부 및 현황
	// 1) 출근 처리
    AttendanceCheckDTO checkIn(String empId);

    // 2) 퇴근 처리
    AttendanceCheckDTO checkOut(String empId);

    // 3) 특정 사원의 출퇴근 기록 조회 (날짜 기준)
    AttendanceCheckDTO getAttendanceByEmpIdAndDate(String empId, LocalDate workDate);
    
    
}
