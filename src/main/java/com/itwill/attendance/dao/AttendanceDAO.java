package com.itwill.attendance.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.itwill.attendance.dto.AttendanceCheckDTO;
import com.itwill.attendance.dto.AttendanceLateDTO;
import com.itwill.attendance.dto.AttendanceLeaveDTO;
import com.itwill.attendance.dto.AttendanceWorkCheckDTO;
import com.itwill.attendance.dto.AttendanceWorkListDTO;

@Mapper
public interface AttendanceDAO {

	// 사원 이름 조회 
	String selectEmpNameByEmpId(String empId);
	
    // 출근 시간 등록
    void insertCheckInTime(String empId);

    // 퇴근 처리
    void insertCheckOutTime(String empId);

    // 출퇴근 기록 조회
    AttendanceCheckDTO selectAttendanceByEmpIdAndDate(String empId, String date);

    // 지각 상세 내역 조회
    List<AttendanceLateDTO> findLatenessRecordsByEmpIdAndDateRange(Map<String, Object> paramMap);

    // 지각 통계 조회
    AttendanceLateDTO countTotalLateStatsByEmpIdAndDateRange(Map<String, Object> paramMap);

    // 근무 통계 조회
    AttendanceWorkCheckDTO findWorkSummaryByEmpIdAndDateRange(Map<String, Object> paramMap);

    // 근태 항목 조회
    AttendanceWorkListDTO findWorkItemByDateAndCategory(AttendanceWorkListDTO dto);

    // 휴가 조회 (단일 날짜)
    List<AttendanceLeaveDTO> selectLeaveByDate(String empId, String date);

    // 휴가 조회 (기간)
    List<AttendanceLeaveDTO> selectLeaveByDateRange(String empId, String startDate, String endDate);

    // 휴가 보고서 조회
    AttendanceLeaveDTO selectLeaveReportById(String leaveId);

    // 휴가 보고서 엑셀/PDF 다운로드
    List<AttendanceLeaveDTO> selectMyLeaveReports(String empId);
}
