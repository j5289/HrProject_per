package com.itwill.attendance.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

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
    	//출퇴근 기록 조회
    	AttendanceCheckDTO result = attendanceMapper.selectAttendanceByEmpIdAndDate(empId, workDate.toString());

         if (result != null && result.getCheckInTime() != null) {
             // 출근 시간이 9시 이후면 지각으로 간주
             LocalTime expectedCheckInTime = LocalTime.of(9, 0); // 9시
             if (result.getCheckInTime().isAfter(expectedCheckInTime)) {
                 result.setIsLate(true); //지각 여부 설정
             } else {
                 result.setIsLate(false); //지각 아님
             }
         }
         
         return result;
     }
   
    
    // ===== 2. 사용자 지각 현황 =====
    // 1) 지각 상세 내역 조회
    @Override
    public List<AttendanceLateDTO> getLateDetailsByEmpIdAndDateRange(Map<String, Object> paramMap) {
        return attendanceMapper.findLatenessRecordsByEmpIdAndDateRange(paramMap);
    }

    // 2) 지각 통계 조회 (총 횟수, 총 시간)
    @Override
    public AttendanceLateDTO getLateStatsByEmpIdAndDateRange(Map<String, Object> paramMap) {
        return attendanceMapper.countTotalLateStatsByEmpIdAndDateRange(paramMap);
    }

    
    // ===== 3. 사용자 근무 조회 =====
    // 1) 기간 내 근무 통계 조회
    @Override
    public AttendanceWorkCheckDTO findWorkSummaryByEmpIdAndDateRange(Map<String, Object> paramMap) {
        return attendanceMapper.findWorkSummaryByEmpIdAndDateRange(paramMap);
    }
    
    // ===== 4. 사원의 근태 항목 조회 =====
    @Override
    public AttendanceWorkListDTO findWorkItemByDateAndCategory(AttendanceWorkListDTO dto) {
    	return attendanceMapper.findWorkItemByDateAndCategory(dto);
    }
    
    
    
    
    
    
}
