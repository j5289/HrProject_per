package com.itwill.attendance.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itwill.attendance.dto.*;
import com.itwill.attendance.mapper.AttendanceMapper;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AttendanceServiceImpl implements AttendanceService {
	
	@Autowired
	private AttendanceMapper attendanceMapper;
	
	// ===== 1. 출퇴근 기록부 및 현황 =====
    // 1) 출근 시간 등록
	@Override
    public AttendanceCheckDTO checkIn(String empId) {
        // 출근 시간 등록 메서드 호출
        attendanceMapper.insertCheckInTime(empId);
        return attendanceMapper.selectAttendanceByEmpIdAndDate(empId, "CURRENT_DATE"); // 출근 후 등록된 기록을 반환
    }

    // 2) 퇴근 처리
    @Override
    public AttendanceCheckDTO checkOut(String empId) {
        // 퇴근 시간 등록 메서드 호출
        attendanceMapper.insertCheckOutTime(empId);
        return attendanceMapper.selectAttendanceByEmpIdAndDate(empId, "CURRENT_DATE"); // 퇴근 후 등록된 기록을 반환
    }

    // 3) 특정 사원의 출퇴근 기록 조회 (날짜 기준)
    @Override
    public AttendanceCheckDTO getAttendanceByEmpIdAndDate(String empId, LocalDate workDate) {
        // 특정 날짜의 출퇴근 기록 조회
        return attendanceMapper.selectAttendanceByEmpIdAndDate(empId,  workDate.toString());
    }
   
    
}
