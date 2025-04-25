package com.itwill.attendance.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.itwill.attendance.dto.AttendanceAdminCheckDTO;
import com.itwill.attendance.dto.AttendanceAdminLeaveDTO;

@Repository
public class AttendanceAdminDAOImpl implements AttendanceAdminDAO {
	
	@Autowired
    private final SqlSession sqlSession;
	
    private static final String NAMESPACE = "com.itwill.attendance.mapper.AttendanceAdminMapper.";

    @Autowired
    public AttendanceAdminDAOImpl(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    // ===== 1. 관리자의 사원 출퇴근 기록 및 현황 조회 ======
    @Override
    public List<AttendanceAdminCheckDTO> selectAdminAttendanceList(Map<String, Object> params) {
        return sqlSession.selectList(NAMESPACE + "selectAdminAttendanceList", params);
    }

    @Override
    public AttendanceAdminCheckDTO selectAdminAttendanceByEmpIdAndDate(Map<String, Object> params) {
        return sqlSession.selectOne(NAMESPACE + "selectAdminAttendanceByEmpIdAndDate", params);
    }

    //===== 2. 관리자의 사원 휴가 목록 조회 =====
    @Override
    public List<AttendanceAdminLeaveDTO> selectLeaveListByAdmin(String empId, String empName) {
        Map<String, Object> params = new HashMap<>();
        params.put("empId", empId);
        params.put("empName", empName);
        return sqlSession.selectList(NAMESPACE + ".selectLeaveListByAdmin", params);
    }
}
