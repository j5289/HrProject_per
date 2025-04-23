package com.itwill.attendance.controller;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.itwill.attendance.dto.AttendanceCheckDTO;
import com.itwill.attendance.dto.AttendanceLateDTO;
import com.itwill.attendance.service.AttendanceService;  

@Controller
@RequestMapping("/attendance")
public class AttendanceController {
	
	@Autowired
	private AttendanceService attendanceService;
	
	// 출퇴근 메인 페이지 진입
	@GetMapping("/main")
	public String showAttendanceMainPage(HttpSession session) {
	    // 로그인 확인용 세션 처리도 여기서 할 수 있음
	    String empId = (String) session.getAttribute("id");
	    if (empId == null) {
	        return "redirect:/member/login"; // 로그인 안 되어 있으면 로그인 페이지로 이동
	    }
	    return "attendance/attendance-main"; // JSP 경로 
	}
	
	// ===== 1. 사용자 출퇴근 기록부 및 현황 =====
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
    
    
    // ===== 2. 사용자 지각 현황 =====
    @PostMapping("/attendance/late-status")
    @ResponseBody
    public Map<String, Object> getLateStatus(
            @RequestParam("empId") String empId,
            @RequestParam("startDate") String startDateStr,
            @RequestParam("endDate") String endDateStr) {

        Map<String, Object> response = new HashMap<>();

        try {
            // 날짜 파싱
            LocalDate startDate = LocalDate.parse(startDateStr);
            LocalDate endDate = LocalDate.parse(endDateStr);

            // 파라미터 맵 생성
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("empId", empId);
            paramMap.put("startDate", startDate);
            paramMap.put("endDate", endDate);

            // 지각 상세 목록 조회
            List<AttendanceLateDTO> lateDetails = attendanceService.getLateDetailsByEmpIdAndDateRange(paramMap);

            // 지각 통계 조회
            AttendanceLateDTO lateStats = attendanceService.getLateStatsByEmpIdAndDateRange(paramMap);

            // 응답 구성
            response.put("lateDetails", lateDetails);  // 지각 내역
            response.put("lateStats", lateStats);      // 지각 통계
            response.put("message", "지각 현황 조회 성공");

        } catch (Exception e) {
            response.put("message", "지각 현황 조회에 실패했습니다.");
        }

        return response;
    }
    
    
    
    
    
    
}
