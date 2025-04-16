package com.itwill.attendance.service;

import com.itwill.attendance.dto.*;
import com.itwill.attendance.mapper.AttendanceMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AttendanceServiceImpl implements AttendanceService {

    private final AttendanceMapper attendanceMapper;

    @Override
    public List<AttendanceDetailDTO> getMyAttendanceRecord(String empId, String startDate, String endDate) {
        return attendanceMapper.getMyAttendanceRecord(empId, startDate, endDate);
    }

    @Override
    public List<LatenessDTO> getMyLateness(String empId, String startDate, String endDate) {
        return attendanceMapper.getMyLateness(empId, startDate, endDate);
    }

    @Override
    public WorkSummaryDTO getWorkSummary(String empId, String startDate, String endDate) {
        return attendanceMapper.getWorkSummary(empId, startDate, endDate);
    }

    @Override
    public List<AttendanceStatusDTO> getMyAttendanceStatus(String empId, String startDate, String endDate) {
        return attendanceMapper.getMyAttendanceStatus(empId, startDate, endDate);
    }

    @Override
    public List<LeaveHistoryDTO> getMyLeaveHistory(String empId) {
        return attendanceMapper.getMyLeaveHistory(empId);
    }

    @Override
    public LeaveBalanceDTO getMyLeaveBalance(String empId) {
        return attendanceMapper.getMyLeaveBalance(empId);
    }

    @Override
    public List<AttendanceDetailDTO> getAttendanceRecordsByCategory(String empId, String departmentId, String startDate, String endDate) {
        return attendanceMapper.getAttendanceRecordsByCategory(empId, departmentId, startDate, endDate);
    }

    @Override
    public void updateLeaveDays(LeaveUpdateRequestDTO dto) {
        attendanceMapper.updateLeaveDays(dto);
    }

    @Override
    public List<LatenessDTO> getAllLatenessRecords(String empId, String departmentId, String startDate, String endDate) {
        return attendanceMapper.getAllLatenessRecords(empId, departmentId, startDate, endDate);
    }

    @Override
    public List<AttendanceStatusDTO> getAllWorkStatus(String empId, String startDate, String endDate) {
        return attendanceMapper.getAllWorkStatus(empId, startDate, endDate);
    }

    @Override
    public void updateAttendanceRecord(AttendanceUpdateDTO dto) {
        attendanceMapper.updateAttendanceRecord(dto);
    }

    @Override
    public List<AttendanceWarningDTO> getAttendanceSummaryForAdmin(String startDate, String endDate) {
        return attendanceMapper.getAttendanceSummaryForAdmin(startDate, endDate);
    }
    
//    @Override
//    public void registerAttendance(AttendanceDTO dto) {
//    // 예시 로직: 현재 시간으로 출근 기록 생성
//    Attendance attendance = Attendance.builder()
//    .attendanceId(dto.getEmployeeId() + "_" + dto.getAttendanceDate()) // 예시
//    .employeeId(dto.getEmployeeId())
//    .attendanceDate(dto.getAttendanceDate())
//    .arrivalTime(LocalDateTime.now())
//    .status(dto.getStatus())
//    .build();
//
//    attendanceMapper.insertAttendance(attendance);
//
//    }
}
