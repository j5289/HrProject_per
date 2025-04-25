package com.itwill.attendance.dao;

import java.util.List;
import java.util.Map;

import com.itwill.attendance.dto.AttendanceAdminCheckDTO;
import com.itwill.attendance.dto.AttendanceAdminLeaveDTO;

public interface AttendanceAdminDAO {

    // 1. 관리자의 사원 출퇴근 기록 및 현황 조회
    List<AttendanceAdminCheckDTO> selectAdminAttendanceList(Map<String, Object> params);

    AttendanceAdminCheckDTO selectAdminAttendanceByEmpIdAndDate(Map<String, Object> params);

    // 2. 관리자용 사원 휴가 목록 조회
    List<AttendanceAdminLeaveDTO> selectLeaveByEmployeeForAdmin(String startDate, String endDate);

}
