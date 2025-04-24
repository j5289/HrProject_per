package com.itwill.attendance.service;

import java.util.List;
import java.util.Map;

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

	    // 통합 조건 기반 전체 조회
	    @Override
	    public List<AttendanceAdminCheckDTO> getAdminAttendanceList(Map<String, Object> params) {
	        return attendanceAdminMapper.selectAdminAttendanceList(params);
	    }

	    // 단건 조회 (예: 상세보기용)
	    @Override
	    public AttendanceAdminCheckDTO getAdminAttendanceDetail(Map<String, Object> params) {
	        return attendanceAdminMapper.selectAdminAttendanceByEmpIdAndDate(params);
	    }
}

	   
