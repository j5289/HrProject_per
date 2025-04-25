package com.itwill.attendance.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.itwill.attendance.dto.AttendanceAdminCheckDTO;
import com.itwill.attendance.dto.AttendanceAdminLeaveDTO;

@Repository
public class AttendanceAdminDAOImpl implements AttendanceAdminDAO {

    private final SqlSession sqlSession;
    private static final String NAMESPACE = "com.itwill.attendance.mapper.AttendanceAdminMapper.";

    @Autowired
    public AttendanceAdminDAOImpl(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    @Override
    public List<AttendanceAdminCheckDTO> selectAdminAttendanceList(Map<String, Object> params) {
        return sqlSession.selectList(NAMESPACE + "selectAdminAttendanceList", params);
    }

    @Override
    public AttendanceAdminCheckDTO selectAdminAttendanceByEmpIdAndDate(Map<String, Object> params) {
        return sqlSession.selectOne(NAMESPACE + "selectAdminAttendanceByEmpIdAndDate", params);
    }

    @Override
    public List<AttendanceAdminLeaveDTO> selectLeaveByEmployeeForAdmin(String startDate, String endDate) {
        return sqlSession.selectList(NAMESPACE + "selectLeaveByEmployeeForAdmin",
                Map.of("startDate", startDate, "endDate", endDate));
    }
}
