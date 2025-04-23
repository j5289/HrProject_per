package com.itwill.attendance.mapper;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.repository.query.Param;
import org.apache.ibatis.annotations.Mapper;
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

}