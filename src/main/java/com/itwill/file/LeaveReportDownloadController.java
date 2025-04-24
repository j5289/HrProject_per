//package com.itwill.file;
//
//import javax.mail.internet.ContentDisposition;
//import javax.servlet.http.HttpSession;
//import java.util.List;
//import java.time.LocalDate;
//import javax.servlet.http.HttpServletResponse;
//
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.itwill.attendance.dto.AttendanceLeaveDTO;
//import com.itwill.attendance.service.AttendanceService;
//import com.itwill.util.ExcelLeaveExporter;
//import com.itwill.util.PdfLeaveExporter;
//
//@RestController
//public class LeaveReportDownloadController {
//
//	private final AttendanceService attendanceService;
//	   
//	// 생성자 주입 (AttendanceService는 필요한 데이터 제공)
//    public LeaveReportDownloadController(AttendanceService attendanceService) {
//        this.attendanceService = attendanceService;
//    }
//
//    @GetMapping("/download/leave-report/{leaveId}/excel")
//    public ResponseEntity<byte[]> downloadLeaveReportExcel(@PathVariable String leaveId) {
//        // leaveId로 해당 휴가 보고서에 대한 데이터를 조회
//        List<AttendanceLeaveDTO> leaveReports = attendanceService.findLeaveReportsByLeaveId(leaveId);
//
//        try {
//            // 엑셀로 변환
//            byte[] excelData = ExcelLeaveExporter.exportLeaveToExcel(leaveReports);
//
//            // HttpHeaders에 Content-Disposition 설정 (파일 다운로드)
//            HttpHeaders headers = new HttpHeaders();
//            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);  // 파일 다운로드를 위한 미디어 타입 설정
//            headers.set("Content-Disposition", "attachment; filename=leave_report_" + leaveId + ".xlsx");
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
//}