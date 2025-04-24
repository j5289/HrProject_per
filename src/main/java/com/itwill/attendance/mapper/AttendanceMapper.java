package com.itwill.attendance.mapper;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.data.repository.query.Param;
import org.apache.ibatis.annotations.Mapper;
import com.itwill.attendance.dto.AttendanceCheckDTO;
import com.itwill.attendance.dto.AttendanceLateDTO;
import com.itwill.attendance.dto.AttendanceLeaveDTO;
import com.itwill.attendance.dto.AttendanceWorkCheckDTO;
import com.itwill.attendance.dto.AttendanceWorkListDTO;
import com.itwill.attendance.model.Attendance;

/**
 * 해당 기능 구현을 위한 메서드 작성하는 곳, 각 기능마다 메서드 이름을 다르게 설정하고 
 * 메서드의 파라미터와 반환값에 맞는 DTO 사용
 */

public interface AttendanceMapper {
	

	//===== 1. 사원의 출퇴근 기록부 및 현황 ======
    // 1) 출근 시간 등록
    void insertCheckInTime(@Param("empId") String empId);

    // 2) 퇴근 시간 등록
    void insertCheckOutTime(@Param("empId") String empId);

    // 3) 특정 사원의 출퇴근 기록 조회 (날짜 기준)
    AttendanceCheckDTO selectAttendanceByEmpIdAndDate(@Param("empId") String empId, @Param("workDate") String workDate);

    // ===== 2. 사원의 지각 현황 =====
    // 1) 특정 사원의 기간 내 지각 기록 전체 조회 (날짜별, 사유서 포함)
    List<AttendanceLateDTO> findLatenessRecordsByEmpIdAndDateRange(Map<String, Object> paramMap);
        
    // 2) 특정 사원의 기간 내 지각 총 횟수 및 총 시간 
    AttendanceLateDTO countTotalLateStatsByEmpIdAndDateRange(Map<String, Object> paramMap);

    // ===== 3. 사원의 근무 조회 =====
    // 1) 특정 사원의 기간 및 근무 유형 기준 근무 통계 요약
    AttendanceWorkCheckDTO findWorkSummaryByEmpIdAndDateRange(Map<String, Object> paramMap);

    // ===== 4. 사원의 근태 항목 조회 =====
    // 1) 사원의 특정 날짜 + 테고리로 근태 항목 조회 
    AttendanceWorkListDTO findWorkItemByDateAndCategory(AttendanceWorkListDTO dto);
    
    // ===== 5. 사원의 휴가 내역 조회 =====
    // 1) 사원의 휴가 내역을 날짜 범위로 조회 return 휴가 내역 리스트 
    List<AttendanceLeaveDTO> selectLeaveByDateRange(
            @Param("empId") String empId,
            @Param("startDate") String startDate,
            @Param("endDate") String endDate
        );
    
    // 2) 단일 날짜에 해당하는 휴가 내역 조회 (2024-05-01 하루에 해당하는 휴가) return 해당 날짜에 있는 휴가 내역 리스트
    List<AttendanceLeaveDTO> selectLeaveByDate(
            @Param("empId") String empId,
            @Param("date") String date
        );
    
    // 3) 휴가 Id로 상세 보고서 조회 return 해당 휴가에 첨부된 보고서 정보 포함 객체
    AttendanceLeaveDTO selectLeaveReportById(@Param("leaveId") String leaveId);

    // ===== 5-1. 사원의 휴가 보고서 엑셀/PDF 다운로드 =====
    List<AttendanceLeaveDTO> selectMyLeaveReports(@Param("empId") String empId);


}