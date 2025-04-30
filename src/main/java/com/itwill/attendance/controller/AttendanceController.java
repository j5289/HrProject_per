package com.itwill.attendance.controller;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.itwill.attendance.dto.AttendanceCheckDTO;
import com.itwill.attendance.dto.AttendanceLateDTO;
import com.itwill.attendance.dto.AttendanceLeaveDTO;
import com.itwill.attendance.dto.AttendanceWorkCheckDTO;
import com.itwill.attendance.service.AttendanceService;

@Controller
@RequestMapping("/attendance")
public class AttendanceController {
    
    @Autowired
    private AttendanceService attendanceService;
    
    // 출퇴근 메인 페이지 진입
    @GetMapping("/attendance-main")
    public String attendanceMainPage(HttpSession session, Model model) {
        // 로그인 확인용 세션 처리도 여기서 할 수 있음
        String empId = (String) session.getAttribute("id");
        if (empId == null) {
            return "redirect:/member/login"; // 로그인 안 되어 있으면 로그인 페이지로 이동
        }
        return "attendance/attendance-main"; // JSP 경로
    }
    
    // ===== 1. 사용자 출퇴근 기록부 및 현황 =====
    // 1) 출근 시간 등록
    @PostMapping("/check-in")
    @ResponseBody
    public ResponseEntity<?> checkIn(@RequestParam("empId") String empId) {
        try {
            // 출근 시간 등록 처리 (이때 반환값은 AttendanceCheckDTO 타입)
            AttendanceCheckDTO attendanceCheckDTO = attendanceService.checkIn(empId);

            // DTO에서 checkInTime을 Timestamp 타입으로 추출
            Timestamp checkInTimestamp = attendanceCheckDTO.getCheckInTime();

            // 만약 checkInTimestamp이 null이라면, 현재 시간으로 설정
            if (checkInTimestamp == null) {
                checkInTimestamp = new Timestamp(System.currentTimeMillis());
            }

            // 응답에 출근 시간 추가 (Timestamp 그대로 전달)
            Map<String, Object> response = new HashMap<>();
            response.put("checkInTime", checkInTimestamp.toString());  // Timestamp를 String으로 변환하여 응답에 추가
            response.put("empName", attendanceCheckDTO.getEmpName()); // 사원 이름도 함께 응답에 추가
            response.put("empId", attendanceCheckDTO.getEmpId()); // 사원 ID도 함께 응답에 추가
            response.put("isLate", attendanceCheckDTO.getLateStatus()); // 지각 여부도 응답에 추가

            return ResponseEntity.ok(response); // 정상 처리 시 응답
        } catch (RuntimeException e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("출근 처리에 실패했습니다: " + e.getMessage()); // 실패 시 상세 메시지 전달
        }
    }


    // 2) 퇴근 시간 등록
    @PostMapping("/check-out")
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
    @PostMapping("/check-attendance")
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
    //  2-1. 지각 현황 조회 화면 보여주기
    @GetMapping("/attendance-late")
    public String showLateStatusPage() {
        return "attendance/attendance-late";
    }

    //  2-2. 사용자 지각 현황 데이터 처리 (AJAX용 등) 
    @PostMapping("/attendance-late")
    @ResponseBody
    public Map<String, Object> getLateStatus(
            @RequestParam("empId") String empId,
            @RequestParam("startDate") String startDateStr,
            @RequestParam("endDate") String endDateStr) {

        Map<String, Object> response = new HashMap<>();

        try {
            LocalDate startDate = LocalDate.parse(startDateStr);
            LocalDate endDate = LocalDate.parse(endDateStr);

            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("empId", empId);
            paramMap.put("startDate", startDate);
            paramMap.put("endDate", endDate);

            List<AttendanceLateDTO> lateDetails = attendanceService.getLateDetailsByEmpIdAndDateRange(paramMap);
            AttendanceLateDTO lateStats = attendanceService.getLateStatsByEmpIdAndDateRange(paramMap);

            response.put("lateDetails", lateDetails);
            response.put("lateStats", lateStats);
            response.put("message", "지각 현황 조회 성공");

        } catch (Exception e) {
            response.put("message", "지각 현황 조회에 실패했습니다.");
        }

        return response;
    }
    
    // ===== 3. 사용자 근무 조회 =====
    @GetMapping("/attendance-summary")
    public String showAttendanceSummaryPage() {
        return "attendance/attendance-summary"; 
    }

    @PostMapping("/attendance-summary")
    @ResponseBody
    public Map<String, Object> getWorkSummary(
            @RequestParam("empId") String empId,
            @RequestParam("startDate") String startDateStr,
            @RequestParam("endDate") String endDateStr) {

        Map<String, Object> response = new HashMap<>();

        try {
            LocalDate startDate = LocalDate.parse(startDateStr);
            LocalDate endDate = LocalDate.parse(endDateStr);

            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("empId", empId);
            paramMap.put("startDate", startDate);
            paramMap.put("endDate", endDate);

            AttendanceWorkCheckDTO workSummary = attendanceService.findWorkSummaryByEmpIdAndDateRange(paramMap);

            response.put("workSummary", workSummary);
            response.put("message", "근무 통계 조회 성공");
        } catch (Exception e) {
            response.put("message", "근무 통계 조회에 실패했습니다.");
        }

        return response;
    }
    
    // 4. 사원의 근태 항목 조회
    @GetMapping("/attendance-items")
    public String showAttendanceItemsPage() {
        return "attendance/attendance-items";  
    }

    @PostMapping("/attendance/attendance-items")
    @ResponseBody
    public ResponseEntity<AttendanceCheckDTO> getAttendanceItems(@RequestBody AttendanceCheckDTO requestDto) {
        AttendanceCheckDTO result = attendanceService.getAttendanceItems(requestDto);
        
        if (result != null) {
            return ResponseEntity.ok(result);  
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);  
        }
    }
    
    // 5. 사원의 휴가 내역 조회
    @GetMapping("/attendance-leave")
    public String showLeavePage() {
        return "attendance/attendance-leave";
    }
    
    @PostMapping("/leave/search")
    public String searchLeave(
        @RequestParam("empId") String empId,
        @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date,
        @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
        @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate,
        Model model
    ) {
        List<AttendanceLeaveDTO> leaveList;

        if (date != null) {
            leaveList = attendanceService.findLeaveByDate(empId, date);
        } else if (startDate != null && endDate != null) {
            leaveList = attendanceService.findLeaveByDateRange(empId, startDate, endDate);
        } else {
            model.addAttribute("alertMsg", "조회 조건을 다시 확인해주세요.");
            return "attendance/leave_list";
        }

        if (leaveList.isEmpty()) {
            model.addAttribute("alertMsg", "해당하는 정보가 존재하지 않습니다.");
        } else {
            model.addAttribute("leaveList", leaveList);
        }

        return "attendance/attendance-leave";
    }
    
    @GetMapping("/leave/report/{leaveId}")
    public String viewLeaveReport(@PathVariable("leaveId") String leaveId, Model model) {
        AttendanceLeaveDTO report = attendanceService.findLeaveReportById(leaveId);
        model.addAttribute("report", report);
        return "attendance/leave_report_detail";
    }
}
