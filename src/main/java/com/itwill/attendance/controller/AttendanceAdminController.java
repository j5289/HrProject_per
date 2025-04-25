package com.itwill.attendance.controller;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.itwill.attendance.dto.AttendanceAdminCheckDTO;
import com.itwill.attendance.dto.AttendanceAdminLeaveDTO;
import com.itwill.attendance.service.AttendanceAdminService;

/**
 * 관리자용 컨트롤러 
 *
 */

@Controller
@RequestMapping("/admin/attendance")
public class AttendanceAdminController{
	
		@Autowired
		private AttendanceAdminService attendanceAdminService;
	
		// ===== 1. 관리자의 사원 출퇴근 기록 및 현황 조회 =====
		// 전체 사원의 출퇴근 기록 리스트 조회(조건 : 이름, 날짜)
		@GetMapping("/list")
	    public String getAdminAttendanceList(
	            @RequestParam(value = "empName", required = false) String empName,
	            @RequestParam(value = "workDate", required = false) Date workDate,
	            Model model) {

	        Map<String, Object> params = new HashMap<>();
	        params.put("empName", empName);
	        params.put("workDate", workDate);

	        //출퇴근 기록 조회
	        List<AttendanceAdminCheckDTO> attendanceList = attendanceAdminService.getAdminAttendanceList(params);

	        // 근무 상태 및 지각 여부 판단
	        for (AttendanceAdminCheckDTO dto : attendanceList) {
	            if (dto.getCheckInTime() != null && dto.getCheckInTime().toLocalDateTime().getHour() >= 9) {
	                dto.setIsLate(true);
	                dto.setWorkStatus("지각");
	            } else if (dto.getCheckInTime() != null && dto.getCheckOutTime() != null) {
	                dto.setWorkStatus("정상 출근");
	            } else if (dto.getCheckInTime() != null) {
	                dto.setWorkStatus("출근");
	            } else {
	                dto.setWorkStatus("미출근");
	            }
	        }

	        // 뷰로 전달
	        model.addAttribute("attendanceList", attendanceList);
	        model.addAttribute("empName", empName);
	        model.addAttribute("workDate", workDate);

	        return "attendance/admin_attendance"; // JSP 또는 Thymeleaf 템플릿
	    }

		// ===== 2. 관리자의 사원 휴가 내역 조회 =====
		@RequestMapping("/leave-list")
		public String getLeaveList(
		        @RequestParam(required = false) String empId,
		        @RequestParam(required = false) String empName,
		        Model model,
		        HttpServletRequest request) {

		    List<AttendanceAdminLeaveDTO> leaveList = attendanceAdminService.getLeaveListByAdmin(empId, empName);

		    if (leaveList == null || leaveList.isEmpty()) {
		        request.setAttribute("message", "해당하는 정보가 존재하지 않습니다.");
		        return "attendance/admin_leave_check"; // alert 띄운 후 이전 페이지로
		    }

		    model.addAttribute("leaveList", leaveList);
		    return "attendance/admin_leave_check"; // 정상 조회 결과
		}
		
}
