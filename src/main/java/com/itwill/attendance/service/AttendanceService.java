package com.itwill.attendance.service;

import java.time.LocalDate;
import java.util.List;

import com.itwill.attendance.dto.AttendanceCheckDTO;
import com.itwill.attendance.dto.AttendanceDTO;
import com.itwill.attendance.dto.AttendanceDetailDTO;
import com.itwill.attendance.dto.AttendanceStatusDTO;
import com.itwill.attendance.dto.AttendanceUpdateDTO;
import com.itwill.attendance.dto.AttendanceWarningDTO;
import com.itwill.attendance.dto.LatenessDTO;
import com.itwill.attendance.dto.LeaveBalanceDTO;
import com.itwill.attendance.dto.LeaveDTO;
import com.itwill.attendance.dto.LeaveHistoryDTO;
import com.itwill.attendance.dto.LeaveUpdateRequestDTO;
import com.itwill.attendance.dto.WorkSummaryDTO;

public interface AttendanceService {

	// ===== 1. 출퇴근 기록부 및 현황
	// 1) 출근 처리
    AttendanceCheckDTO checkIn(String empId);

    // 2) 퇴근 처리
    AttendanceCheckDTO checkOut(String empId);

    // 3) 특정 사원의 출퇴근 기록 조회 (날짜 기준)
    AttendanceCheckDTO getAttendanceByEmpIdAndDate(String empId, LocalDate workDate);
    
    
}
