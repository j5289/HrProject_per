package com.itwill.attendance.dao;

import java.util.List;
import java.util.Map;

import com.itwill.attendance.dto.AttendanceCheckDTO;
import com.itwill.attendance.dto.AttendanceLateDTO;
import com.itwill.attendance.dto.AttendanceLeaveDTO;
import com.itwill.attendance.dto.AttendanceWorkCheckDTO;
import com.itwill.attendance.dto.AttendanceWorkListDTO;

public interface AttendanceDAO {

    // 출퇴근 등록
    void insertCheckInTime(String empId);
    void insertCheckOutTime(String empId);

    // 출퇴근 조회
    AttendanceCheckDTO selectAttendanceByEmpIdAndDate(String empId, String workDate);

    // 지각
    List<AttendanceLateDTO> findLatenessRecordsByEmpIdAndDateRange(Map<String, Object> paramMap);
    AttendanceLateDTO countTotalLateStatsByEmpIdAndDateRange(Map<String, Object> paramMap);

    // 근무 요약
    AttendanceWorkCheckDTO findWorkSummaryByEmpIdAndDateRange(Map<String, Object> paramMap);

    // 근태 항목
    AttendanceWorkListDTO findWorkItemByDateAndCategory(AttendanceWorkListDTO dto);

    // 휴가 내역
    List<AttendanceLeaveDTO> selectLeaveByDateRange(String empId, String startDate, String endDate);
    List<AttendanceLeaveDTO> selectLeaveByDate(String empId, String date);
    AttendanceLeaveDTO selectLeaveReportById(String leaveId);

    // 휴가 보고서 리스트
    List<AttendanceLeaveDTO> selectMyLeaveReports(String empId);
}
