package com.itwill.attendance.dao;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.itwill.attendance.dto.AttendanceAdminCheckDTO;
import com.itwill.attendance.dto.AttendanceAdminLateDTO;
import com.itwill.attendance.dto.AttendanceAdminLeaveDTO;
import com.itwill.attendance.dto.AttendanceAdminUpdateAndDeleteDTO;
import com.itwill.attendance.dto.AttendanceAdminWorkDTO;

@Repository
public class AttendanceAdminDAOImpl implements AttendanceAdminDAO {

    private final SqlSession sqlSession;

    private static final String NAMESPACE = "com.itwill.attendance.mapper.AttendanceAdminMapper.";

    @Autowired
    public AttendanceAdminDAOImpl(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    // ===== 1. 관리자의 사원 출퇴근 기록 및 현황 조회 =====
    @Override
    public List<AttendanceAdminCheckDTO> selectAdminAttendanceList(Map<String, Object> params) {
        return sqlSession.selectList(NAMESPACE + "selectAdminAttendanceList", params);
    }

    @Override
    public AttendanceAdminCheckDTO selectAdminAttendanceByEmpIdAndDate(Map<String, Object> params) {
        return sqlSession.selectOne(NAMESPACE + "selectAdminAttendanceByEmpIdAndDate", params);
    }

    // ===== 2. 관리자의 사원 휴가 목록 조회 =====
    @Override
    public List<AttendanceAdminLeaveDTO> selectLeaveListByAdmin(String empId, String empName) {
        Map<String, Object> params = new HashMap<>();
        params.put("empId", empId);
        params.put("empName", empName);
        return sqlSession.selectList(NAMESPACE + "selectLeaveListByAdmin", params);
    }

    // ===== 3. 관리자의 사원 지각 조회 =====
    @Override
    public List<AttendanceAdminLateDTO> getLateStatusByAdmin(Map<String, Object> params) {
        return sqlSession.selectList(NAMESPACE + "getLateStatusByAdmin", params);
    }

    // ===== 4. 관리자의 사원 근무 조회 및 근무 입력 =====
    @Override
    public List<AttendanceAdminWorkDTO> getWorkStatusByAdmin(Map<String, Object> params) {
        return sqlSession.selectList(NAMESPACE + "getWorkStatusByAdmin", params);
    }

    @Override
    public AttendanceAdminUpdateAndDeleteDTO selectWorkDetail(String empId, Date workDate) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("empId", empId);
        paramMap.put("workDate", workDate);
        return sqlSession.selectOne(NAMESPACE + "selectWorkDetail", paramMap);
    }

    @Override
    public int updateWorkStatus(AttendanceAdminUpdateAndDeleteDTO dto) {
        return sqlSession.update(NAMESPACE + "updateWorkStatus", dto);
    }

    @Override
    public int insertWorkStatus(AttendanceAdminWorkDTO dto) {
        return sqlSession.insert(NAMESPACE + "insertWorkStatus", dto);  // DTO 그대로 전달
    }

    @Override
    public int deleteWorkStatus(AttendanceAdminUpdateAndDeleteDTO dto) {
        return sqlSession.delete(NAMESPACE + "deleteWorkStatus", dto);
    }
}
