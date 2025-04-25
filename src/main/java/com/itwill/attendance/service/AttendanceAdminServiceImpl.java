package com.itwill.attendance.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itwill.attendance.dao.AttendanceAdminDAO;
import com.itwill.attendance.dto.AttendanceAdminCheckDTO;
import com.itwill.attendance.dto.AttendanceAdminLeaveDTO;

@Service
public class AttendanceAdminServiceImpl implements AttendanceAdminService {

    private final AttendanceAdminDAO attendanceAdminDAO;

    @Autowired
    public AttendanceAdminServiceImpl(AttendanceAdminDAO attendanceAdminDAO) {
        this.attendanceAdminDAO = attendanceAdminDAO;
    }

    // ===== 1. 관리자의 사원 출퇴근 기록 및 현황 조회 =====
    // 관리자의 사원 출퇴근 기록 및 현황 조회
    @Override
    public List<AttendanceAdminCheckDTO> getAdminAttendanceList(Map<String, Object> params) {
        return attendanceAdminDAO.selectAdminAttendanceList(params);
    }

    // 관리자의 특정 사원의 특정 날짜 근무 기록 조회
    @Override
    public AttendanceAdminCheckDTO getAdminAttendanceDetail(Map<String, Object> params) {
        return attendanceAdminDAO.selectAdminAttendanceByEmpIdAndDate(params);
    }

    // ===== 2. 관리자의 사원 휴가 목록 조회 =====
    @Override
    public List<AttendanceAdminLeaveDTO> getLeaveListByAdmin(String empId, String empName) {
        return attendanceAdminDAO.selectLeaveListByAdmin(empId, empName);
    }
    
}
