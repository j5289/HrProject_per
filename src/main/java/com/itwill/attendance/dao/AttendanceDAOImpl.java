package com.itwill.attendance.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.itwill.attendance.dto.AttendanceCheckDTO;
import com.itwill.attendance.dto.AttendanceLateDTO;
import com.itwill.attendance.dto.AttendanceLeaveDTO;
import com.itwill.attendance.dto.AttendanceWorkCheckDTO;
import com.itwill.attendance.dto.AttendanceWorkListDTO;

@Repository
public class AttendanceDAOImpl implements AttendanceDAO {

    private final SqlSession sqlSession;
    private static final String NAMESPACE = "com.itwill.attendance.mapper.AttendanceMapper.";

    @Autowired
    public AttendanceDAOImpl(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    @Override
    public void insertCheckInTime(String empId) {
        sqlSession.insert(NAMESPACE + "insertCheckInTime", empId);
    }

    @Override
    public void insertCheckOutTime(String empId) {
        sqlSession.insert(NAMESPACE + "insertCheckOutTime", empId);
    }

    @Override
    public AttendanceCheckDTO selectAttendanceByEmpIdAndDate(String empId, String workDate) {
        return sqlSession.selectOne(NAMESPACE + "selectAttendanceByEmpIdAndDate",
        		Map.of("empId", empId, "workDate", workDate));
    }

    @Override
    public List<AttendanceLateDTO> findLatenessRecordsByEmpIdAndDateRange(Map<String, Object> paramMap) {
        return sqlSession.selectList(NAMESPACE + "findLatenessRecordsByEmpIdAndDateRange", paramMap);
    }

    @Override
    public AttendanceLateDTO countTotalLateStatsByEmpIdAndDateRange(Map<String, Object> paramMap) {
        return sqlSession.selectOne(NAMESPACE + "countTotalLateStatsByEmpIdAndDateRange", paramMap);
    }

    @Override
    public AttendanceWorkCheckDTO findWorkSummaryByEmpIdAndDateRange(Map<String, Object> paramMap) {
        return sqlSession.selectOne(NAMESPACE + "findWorkSummaryByEmpIdAndDateRange", paramMap);
    }

    @Override
    public AttendanceWorkListDTO findWorkItemByDateAndCategory(AttendanceWorkListDTO dto) {
        return sqlSession.selectOne(NAMESPACE + "findWorkItemByDateAndCategory", dto);
    }

    @Override
    public List<AttendanceLeaveDTO> selectLeaveByDateRange(String empId, String startDate, String endDate) {
        return sqlSession.selectList(NAMESPACE + "selectLeaveByDateRange", 
                Map.of("empId", empId, "startDate", startDate, "endDate", endDate));
    }

    @Override
    public List<AttendanceLeaveDTO> selectLeaveByDate(String empId, String date) {
        return sqlSession.selectList(NAMESPACE + "selectLeaveByDate",
                Map.of("empId", empId, "date", date));
    }

    @Override
    public AttendanceLeaveDTO selectLeaveReportById(String leaveId) {
        return sqlSession.selectOne(NAMESPACE + "selectLeaveReportById", leaveId);
    }

    @Override
    public List<AttendanceLeaveDTO> selectMyLeaveReports(String empId) {
        return sqlSession.selectList(NAMESPACE + "selectMyLeaveReports", empId);
    }
}
