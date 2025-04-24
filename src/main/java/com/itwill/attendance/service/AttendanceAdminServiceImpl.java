package com.itwill.attendance.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itwill.attendance.dto.AttendanceAdminCheckDTO;
import com.itwill.attendance.mapper.AttendanceAdminMapper;

@Service
public class AttendanceAdminServiceImpl implements AttendanceAdminService{

	  private final AttendanceAdminMapper attendanceAdminMapper;

	    @Autowired
	    public AttendanceAdminServiceImpl(AttendanceAdminMapper attendanceAdminMapper) {
	        this.attendanceAdminMapper = attendanceAdminMapper;
	    }

	    // 전체 사원 근무 기록 조회
	    @Override
	    public List<AttendanceAdminCheckDTO> getAllEmployeeAttendance() {
	        return attendanceAdminMapper.selectAllAttendance();
	    }

	    // 특정 사원 근무 기록 조회
	    @Override
	    public List<AttendanceAdminCheckDTO> getEmployeeAttendanceByEmpId(String empId) {
	        return attendanceAdminMapper.selectAttendanceByEmpId(empId);
	    }

	    // 특정 날짜의 전체 근무 기록 조회
	    @Override
	    public List<AttendanceAdminCheckDTO> getAttendanceByDate(String workDate) {
	        return attendanceAdminMapper.selectAttendanceByDate(workDate);
	    }
}
