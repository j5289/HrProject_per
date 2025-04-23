package com.itwill.attendance.mapper;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.data.repository.query.Param;
import org.apache.ibatis.annotations.Mapper;
import com.itwill.attendance.dto.AttendanceCheckDTO;
import com.itwill.attendance.dto.AttendanceLateDTO;
import com.itwill.attendance.dto.AttendanceWorkCheckDTO;
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






}