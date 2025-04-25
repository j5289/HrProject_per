package com.itwill.attendance.controller;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
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
import com.itwill.attendance.dto.AttendanceWorkListDTO;
import com.itwill.attendance.service.AttendanceService;
//import com.itwill.util.ExcelLeaveExporter;  

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
	    return "attendance-main"; // JSP 경로
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
    
    
    // ===== 3. 사용자 근무 조회 =====
    @PostMapping("/attendance/work-summary")
    @ResponseBody
    public Map<String, Object> getWorkSummary(
            @RequestParam("empId") String empId,
            @RequestParam("startDate") String startDateStr,
            @RequestParam("endDate") String endDateStr) {

        Map<String, Object> response = new HashMap<>();

        try {
            // 날짜 파싱
            LocalDate startDate = LocalDate.parse(startDateStr);
            LocalDate endDate = LocalDate.parse(endDateStr);

            // 파라미터 맵 구성
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("empId", empId);
            paramMap.put("startDate", startDate);
            paramMap.put("endDate", endDate);

            // 서비스 호출
            AttendanceWorkCheckDTO workSummary = attendanceService.findWorkSummaryByEmpIdAndDateRange(paramMap);

            // 응답 구성
            response.put("workSummary", workSummary);
            response.put("message", "근무 통계 조회 성공");
        } catch (Exception e) {
            response.put("message", "근무 통계 조회에 실패했습니다.");
        }

        return response;
    }

    
    // ===== 4. 사원의 근태 항목 조회 =====
    // 1) 사원의 특정 날짜에 대한 근태 항목 조회(카테고리별)
    @PostMapping("/work-item")
    public AttendanceWorkListDTO getWorkItemByDate(@RequestBody AttendanceWorkListDTO requestDto) {
        return attendanceService.findWorkItemByDateAndCategory(requestDto);
    }

    
    // ===== 5. 사원의 휴가 내역 조회 =====
    // 1) 휴가 내역 조회 페이지 이동
    @GetMapping("/leave")
    public String showLeavePage() {
        return "attendance/leave_list";
    }
    
    // 2) 휴가 내역 조회 (조회 조건 : 단일 날짜 / 기간)
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

        return "attendance/leave_list";
    }

    
    // 3) 휴가 보고서 상세 보기 
    @GetMapping("/leave/report/{leaveId}")
    public String viewLeaveReport(@PathVariable("leaveId") String leaveId, Model model) {
        AttendanceLeaveDTO report = attendanceService.findLeaveReportById(leaveId);
        model.addAttribute("report", report);
        return "attendance/leave_report_detail";
    }
    
//    // ===== 5-1. 사원의 휴가보고서 엑셀/PDF 다운로드 =====
//    // 생성자 주입
//    public AttendanceController(AttendanceService attendanceService) {
//        this.attendanceService = attendanceService;
//    }
//    
//    @GetMapping("/download/my-leave-reports/{empId}/excel")
//    public ResponseEntity<byte[]> downloadMyLeaveReportsExcel(@PathVariable String empId) {
//        // 해당 사원의 휴가 보고서 조회
//        List<AttendanceLeaveDTO> leaveReports = attendanceService.findMyLeaveReports(empId);
//
//        try {
//            // 엑셀로 변환
//            byte[] excelData = ExcelLeaveExporter.exportLeaveToExcel(leaveReports);
//
//            // HttpHeaders에 Content-Disposition 설정 (파일 다운로드)
//            HttpHeaders headers = new HttpHeaders();
//            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);  // 파일 다운로드를 위한 미디어 타입 설정
//            headers.set("Content-Disposition", "attachment; filename=my_leave_report_" + empId + ".xlsx");
//
//            // ResponseEntity로 반환
//            return ResponseEntity.ok()
//                    .headers(headers)
//                    .body(excelData);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResponseEntity.status(500).body(null); // 예외 처리 (에러 시 500 반환)
//        }
//    }
//
//    // 향후 PDF 다운로드 기능도 추가 가능 (추후 PDF 다운로드 로직 추가)
//
//    
    
}
