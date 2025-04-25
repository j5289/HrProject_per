package com.itwill.attendance.dao;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.itwill.attendance.dto.AttendanceAdminCheckDTO;
import com.itwill.attendance.dto.AttendanceAdminLateDTO;
import com.itwill.attendance.dto.AttendanceAdminLeaveDTO;
import com.itwill.attendance.dto.AttendanceAdminUpdateAndDeleteDTO;
import com.itwill.attendance.dto.AttendanceAdminWorkDTO;

public interface AttendanceAdminDAO {

    // 1. 관리자의 사원 출퇴근 기록 및 현황 조회
    List<AttendanceAdminCheckDTO> selectAdminAttendanceList(Map<String, Object> params);

    AttendanceAdminCheckDTO selectAdminAttendanceByEmpIdAndDate(Map<String, Object> params);

    // 2. 관리자용 사원 휴가 목록 조회
    List<AttendanceAdminLeaveDTO> selectLeaveListByAdmin(String empId, String empName);

    // 3. 관리자의 사원 지각 조회 
    List<AttendanceAdminLateDTO> getLateStatusByAdmin(Map<String, Object> params);

    // 4. 관리자의 사원 근무 조회 및 근무 입력
    // 관리자 근무 상태 리스트 조회
    List<AttendanceAdminWorkDTO> getWorkStatusByAdmin(Map<String, Object> params);

    // 관리자 근무 상세 조회
    AttendanceAdminUpdateAndDeleteDTO selectWorkDetail(@Param("empId") String empId, @Param("workDate") Date workDate);

    // 관리자 근무 상태 수정
    int updateWorkStatus(AttendanceAdminUpdateAndDeleteDTO dto);

    // 관리자 근무 상태 등록
    int insertWorkStatus(Map<String, String> params);

    // 관리자 근무 상태 삭제
    int deleteWorkStatus(AttendanceAdminUpdateAndDeleteDTO dto);
}
