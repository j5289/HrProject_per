package com.itwill.attendance.service;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import com.itwill.attendance.dto.AttendanceAdminCheckDTO;
import com.itwill.attendance.dto.AttendanceAdminLateDTO;
import com.itwill.attendance.dto.AttendanceAdminLeaveDTO;
import com.itwill.attendance.dto.AttendanceAdminUpdateAndDeleteDTO;
import com.itwill.attendance.dto.AttendanceAdminWorkDTO;

public interface AttendanceAdminService {

    // ===== 1. 관리자용 사원의 출퇴근 기록 및 현황 조회 =====
    List<AttendanceAdminCheckDTO> getAdminAttendanceList(Map<String, Object> params);
    AttendanceAdminCheckDTO getAdminAttendanceDetail(Map<String, Object> params);

    // ===== 2. 관리자용 사원 휴가 목록 조회 =====
    List<AttendanceAdminLeaveDTO> getLeaveListByAdmin(String empId, String empName);
    
    // ===== 3. 관리자용 지각 현황 조회 =====
    List<AttendanceAdminLateDTO> getLateStatusByAdmin(Map<String, Object> params);

    // ===== 4. 관리자용 근무 현황 조회 및 CRUD =====
    List<AttendanceAdminWorkDTO> getWorkStatusByAdmin(Map<String, Object> params);
    AttendanceAdminUpdateAndDeleteDTO getWorkDetail(String empId, Date workDate);
    boolean updateWorkStatus(AttendanceAdminUpdateAndDeleteDTO dto);
    
    // ✅ 수정된 부분: Map → DTO로 변경
    boolean insertWorkStatus(AttendanceAdminWorkDTO dto);
    
    boolean deleteWorkStatus(AttendanceAdminUpdateAndDeleteDTO dto);
}
