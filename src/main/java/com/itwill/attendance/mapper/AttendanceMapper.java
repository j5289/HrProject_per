package com.itwill.attendance.mapper;

import com.itwill.attendance.dto.*;
import com.itwill.attendance.model.Attendance;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AttendanceMapper {

    List<AttendanceDetailDTO> getMyAttendanceRecord(String empId, String startDate, String endDate);
    List<LatenessDTO> getMyLateness(String empId, String startDate, String endDate);
    WorkSummaryDTO getWorkSummary(String empId, String startDate, String endDate);
    List<AttendanceStatusDTO> getMyAttendanceStatus(String empId, String startDate, String endDate);
    List<LeaveHistoryDTO> getMyLeaveHistory(String empId);
    LeaveBalanceDTO getMyLeaveBalance(String empId);
    List<AttendanceDetailDTO> getAttendanceRecordsByCategory(String empId, String departmentId, String startDate, String endDate);
    void updateLeaveDays(LeaveUpdateRequestDTO dto);
    List<LatenessDTO> getAllLatenessRecords(String empId, String departmentId, String startDate, String endDate);
    List<AttendanceStatusDTO> getAllWorkStatus(String empId, String startDate, String endDate);
    void updateAttendanceRecord(AttendanceUpdateDTO dto);
    List<AttendanceWarningDTO> getAttendanceSummaryForAdmin(String startDate, String endDate);
    void insertAttendance(Attendance attendance);

}
