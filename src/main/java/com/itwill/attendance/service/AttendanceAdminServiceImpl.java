package com.itwill.attendance.service;

import java.sql.Date;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itwill.attendance.dao.AttendanceAdminDAO;
import com.itwill.attendance.dto.AttendanceAdminCheckDTO;
import com.itwill.attendance.dto.AttendanceAdminLateDTO;
import com.itwill.attendance.dto.AttendanceAdminLeaveDTO;
import com.itwill.attendance.dto.AttendanceAdminUpdateAndDeleteDTO;
import com.itwill.attendance.dto.AttendanceAdminWorkDTO;

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
    
    // ===== 3. 관리자의 사원 지각 조회 =====
    @Override
    public List<AttendanceAdminLateDTO> getLateStatusByAdmin(Map<String, Object> params) {
        return attendanceAdminDAO.getLateStatusByAdmin(params);
    }
    
    // ===== 4. 관리자의 사원 근무 조회 및 근무 입력
    @Override
    public List<AttendanceAdminWorkDTO> getWorkStatusByAdmin(Map<String, Object> params) {
        return attendanceAdminDAO.getWorkStatusByAdmin(params);
    }

    @Override
    public AttendanceAdminUpdateAndDeleteDTO getWorkDetail(String empId, Date workDate) {
        return attendanceAdminDAO.selectWorkDetail(empId, workDate);
    }

    @Override
    public boolean updateWorkStatus(AttendanceAdminUpdateAndDeleteDTO dto) {
        return attendanceAdminDAO.updateWorkStatus(dto) > 0;
    }

    @Override
    public boolean insertWorkStatus(Map<String, String> params) {
        return attendanceAdminDAO.insertWorkStatus(params) > 0;
    }

    @Override
    public boolean deleteWorkStatus(AttendanceAdminUpdateAndDeleteDTO dto) {
        return attendanceAdminDAO.deleteWorkStatus(dto) > 0;
    }
    
    
}
