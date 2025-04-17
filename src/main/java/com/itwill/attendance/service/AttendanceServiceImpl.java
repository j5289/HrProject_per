package com.itwill.attendance.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itwill.attendance.dto.*;
import com.itwill.attendance.mapper.AttendanceMapper;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AttendanceServiceImpl implements AttendanceService {
	
	@Autowired
    private  AttendanceMapper attendanceMapper;

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

    @Override
    public void clockIn(String empId) {
        // 출근 기록 추가
        AttendanceDTO attendanceDTO = AttendanceDTO.builder()
            .empId(empId)
            .status("출근")  // 출근 상태
            .clockIn(LocalDateTime.now())  // 현재 시간을 출근 시간으로 설정
            .build();

        attendanceMapper.insertAttendance(attendanceDTO); // 출근 정보 DB에 저장
    }

    @Override
    public void clockOut(String empId) {
        // 퇴근 기록 추가
        AttendanceDTO attendanceDTO = AttendanceDTO.builder()
            .empId(empId)
            .status("퇴근")  // 퇴근 상태
            .clockOut(LocalDateTime.now())  // 현재 시간을 퇴근 시간으로 설정
            .build();

        attendanceMapper.updateAttendance(attendanceDTO); // 퇴근 정보 DB에 저장
    }

    @Override
    public void registerAttendance(AttendanceDTO attendanceDTO) {
        if ("출근".equals(attendanceDTO.getStatus())) {
            // 출근 기록 추가
            attendanceMapper.insertAttendance(attendanceDTO);  // 출근 기록 DB에 저장
        } else if ("퇴근".equals(attendanceDTO.getStatus())) {
            // 퇴근 기록 추가
            attendanceMapper.updateAttendance(attendanceDTO);  // 퇴근 기록 DB에 저장
        }
    }


}
