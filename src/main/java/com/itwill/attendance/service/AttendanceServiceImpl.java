package com.itwill.attendance.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itwill.attendance.dto.*;
import com.itwill.attendance.dao.AttendanceDAO;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AttendanceServiceImpl implements AttendanceService {

    private final AttendanceDAO attendanceDAO;

    // ===== 1. 출퇴근 기록부 및 현황 =====
    // 1) 출근 시간 등록
    @Override
    public AttendanceCheckDTO checkIn(String empId) {
        try {
            attendanceDAO.insertCheckInTime(empId);
            return attendanceDAO.selectAttendanceByEmpIdAndDate(empId, "CURRENT_DATE");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("출근 처리 중 오류 발생: " + e.getMessage());
        }
    }
    
    // 2) 퇴근 처리
    @Override
    public AttendanceCheckDTO checkOut(String empId) {
        attendanceDAO.insertCheckOutTime(empId);
        return attendanceDAO.selectAttendanceByEmpIdAndDate(empId, "CURRENT_DATE");
    }

    // 3) 특정 사원의 출퇴근 기록 조회 (날짜 기준)
    @Override
    public AttendanceCheckDTO getAttendanceByEmpIdAndDate(String empId, LocalDate workDate) {
        AttendanceCheckDTO result = attendanceDAO.selectAttendanceByEmpIdAndDate(empId, workDate.toString());

        if (result != null && result.getCheckInTime() != null) {
            LocalTime checkInTime = result.getCheckInTime().toLocalDateTime().toLocalTime();
            LocalTime expectedCheckInTime = LocalTime.of(9, 0); 

            result.setIsLate(checkInTime.isAfter(expectedCheckInTime));
        }

        return result;
    }

    // ===== 2. 사용자 지각 현황 =====
    // 1) 지각 상세 내역 조회
    @Override
    public List<AttendanceLateDTO> getLateDetailsByEmpIdAndDateRange(Map<String, Object> paramMap) {
        return attendanceDAO.findLatenessRecordsByEmpIdAndDateRange(paramMap);
    }

    // 2) 지각 통계 조회 (총 횟수, 총 시간)
    @Override
    public AttendanceLateDTO getLateStatsByEmpIdAndDateRange(Map<String, Object> paramMap) {
        return attendanceDAO.countTotalLateStatsByEmpIdAndDateRange(paramMap);
    }

    // ===== 3. 사용자 근무 조회 =====
    // 1) 기간 내 근무 통계 조회
    @Override
    public AttendanceWorkCheckDTO findWorkSummaryByEmpIdAndDateRange(Map<String, Object> paramMap) {
        return attendanceDAO.findWorkSummaryByEmpIdAndDateRange(paramMap);
    }
    
    // ===== 4. 사원의 근태 항목 조회 =====
    @Override
    public AttendanceWorkListDTO findWorkItemByDateAndCategory(AttendanceWorkListDTO dto) {
        return attendanceDAO.findWorkItemByDateAndCategory(dto);
    }

    // 출퇴근 기록 조회 메서드
    @Override
    public AttendanceCheckDTO getAttendanceItems(AttendanceCheckDTO requestDto) {
        // requestDto를 바탕으로 DB에서 출퇴근 정보를 조회
        // 예시로, requestDto의 empId와 workDate를 이용해 조회한다고 가정
        return attendanceDAO.selectAttendanceByEmpIdAndDate(requestDto.getEmpId(), requestDto.getWorkDate().toString());
    }
    
    // ===== 5. 사원의 휴가 내역 조회 =====
    // 1) 단일 날짜 기준 휴가 조회
    @Override
    public List<AttendanceLeaveDTO> findLeaveByDate(String empId, LocalDate date) {
        return attendanceDAO.selectLeaveByDate(empId, date.toString());
    }

    // 2) 기간 기준 휴가 조회
    @Override
    public List<AttendanceLeaveDTO> findLeaveByDateRange(String empId, LocalDate startDate, LocalDate endDate) {
        return attendanceDAO.selectLeaveByDateRange(empId, startDate.toString(), endDate.toString());
    }

    // 3) 휴가 보고서 단건 조회
    @Override
    public AttendanceLeaveDTO findLeaveReportById(String leaveId) {
        return attendanceDAO.selectLeaveReportById(leaveId);
    }

    // ===== 5-1. 사원의 휴가 보고서 엑셀/PDF 다운로드 
    @Override
    public List<AttendanceLeaveDTO> getMyLeaveReports(String empId) {
        return attendanceDAO.selectMyLeaveReports(empId);
    }
}
