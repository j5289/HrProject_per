package com.itwill.attendance.controller;

import javax.servlet.http.HttpSession;  // HttpSession 추가

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.itwill.attendance.dto.AttendanceCheckDTO;
import com.itwill.attendance.dto.AttendanceDTO;
import com.itwill.attendance.dto.AttendanceDetailDTO;
import com.itwill.attendance.dto.AttendanceStatusDTO;
import com.itwill.attendance.dto.AttendanceUpdateDTO;
import com.itwill.attendance.dto.AttendanceWarningDTO;
import com.itwill.attendance.dto.LatenessDTO;
import com.itwill.attendance.dto.LeaveBalanceDTO;
import com.itwill.attendance.dto.LeaveDTO;
import com.itwill.attendance.dto.LeaveHistoryDTO;
import com.itwill.attendance.dto.LeaveUpdateRequestDTO;
import com.itwill.attendance.dto.WorkSummaryDTO;
import com.itwill.attendance.service.AttendanceService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/attendance/attendance_main")
public class AttendanceController {
	
	@Autowired
	private AttendanceService attendanceService;
	
	// ===== 1. 출퇴근 기록부 및 현황 =====
    // 1) 출근 시간 등록
    @PostMapping("attendance/check-in")
    @ResponseBody
    public Map<String, Object> checkIn(@RequestParam("empId") String empId) {
        Map<String, Object> response = new HashMap<>();
        try {
            // 출근 처리 메서드 호출
            AttendanceCheckDTO attendanceCheckDTO = attendanceService.checkIn(empId);
            
            // 응답 데이터에 출근 시간 추가
            response.put("checkInTime", attendanceCheckDTO.getCheckInTime());
            response.put("message", "출근 완료!");
        } catch (Exception e) {
            response.put("message", "출근 처리에 실패했습니다.");
        }
        return response;
    }

    // 2) 퇴근 시간 등록
    @PostMapping("attendance/check-out")
    @ResponseBody
    public Map<String, Object> checkOut(@RequestParam("empId") String empId) {
        Map<String, Object> response = new HashMap<>();
        try {
            // 퇴근 처리 메서드 호출
            AttendanceCheckDTO attendanceCheckDTO = attendanceService.checkOut(empId);
            
            // 응답 데이터에 퇴근 시간 추가
            response.put("checkOutTime", attendanceCheckDTO.getCheckOutTime());
            response.put("message", "퇴근 완료!");
        } catch (Exception e) {
            response.put("message", "퇴근 처리에 실패했습니다.");
        }
        return response;
    }


	
	// 3) 특정 사원의 출퇴근 기록 조회 (날짜 기준)
    @PostMapping("/attendance/check-attendance")
    @ResponseBody
    public Map<String, Object> checkAttendance(@RequestParam("empId") String empId, @RequestParam("workDate") String workDateStr) {
        Map<String, Object> response = new HashMap<>();
        try {
            // 출퇴근 기록 조회 서비스 호출
            LocalDate workDate = LocalDate.parse(workDateStr);
            
            // 출퇴근 기록 조회 서비스 호출
            AttendanceCheckDTO result = attendanceService.getAttendanceByEmpIdAndDate(empId, workDate);

            if (result != null) {
                response.put("attendance", result); 
                response.put("lateStatus", result.getLateStatus()); //지각 여부 추가
                response.put("message", "출퇴근 기록 조회 성공");
            } else {
                response.put("message", "해당 날짜의 출퇴근 기록이 없습니다.");
            }
        } catch (Exception e) {
            response.put("message", "출퇴근 기록 조회에 실패했습니다.");
        }
        return response;
    }

}
