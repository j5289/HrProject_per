package com.itwill.attendance.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
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
	@PostMapping("/attendance/check-in")
	@ResponseBody
	public ResponseEntity<?> checkIn(@RequestParam("empId") String empId) {
	    try {
	        AttendanceCheckDTO dto = attendanceService.checkIn(empId);
	        return ResponseEntity.ok(dto); // 정상 처리 시 응답
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
        // /WEB-INF/views/attendance/late_status.jsp 페이지를 보여줍니다.
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
	 // 근무 통계 페이지를 보여주는 GET 방식 컨트롤러
	 @GetMapping("/attendance-summary")
	 public String showAttendanceSummaryPage() {
	     return "attendance/attendance-summary"; 
	 }
	
	 // 근무 통계 조회를 처리하는 POST 방식
	 @PostMapping("/attendance-summary")
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
    // 1) /attendance/attendance-items 페이지 이동
    @GetMapping("/attendance-items")
    public String showAttendanceItemsPage() {
        return "attendance/attendance-items";  // attendance-items.jsp
    }

    // 2) POST 요청을 처리하여 데이터를 반환하는 메서드
    @PostMapping("/attendance/attendance-items")
    @ResponseBody
    public ResponseEntity<AttendanceCheckDTO> getAttendanceItems(@RequestBody AttendanceCheckDTO requestDto) {
        // requestDto로 받은 데이터 처리
        AttendanceCheckDTO result = attendanceService.getAttendanceItems(requestDto);
        
        if (result != null) {
            return ResponseEntity.ok(result);  // 데이터가 있으면 200 OK 반환
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);  // 데이터가 없으면 404 반환
        }
    }

    
    // ===== 5. 사원의 휴가 내역 조회 =====
    // 1) 휴가 내역 조회 페이지 이동
    @GetMapping("/attendance-leave")
    public String showLeavePage() {
        return "attendance/attendance-leave";
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

        return "attendance/attendance-leave";
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
