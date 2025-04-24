package com.itwill.attendance.controller;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.itwill.attendance.dto.AttendanceAdminCheckDTO;
import com.itwill.attendance.service.AttendanceAdminService;

/**
 * 관리자용 컨트롤러 
 *
 */

@Controller
@RequestMapping("/admin/attendance")
public class AttendanceAdminController {

	 @Autowired
	    private AttendanceAdminService attendanceAdminService;

	    // ===== 1. 관리자의 사원 출퇴근 기록 및 현황 조회 =====
	 @GetMapping("/list")
	    public String getAdminAttendanceList(
	            @RequestParam(value = "empName", required = false) String empName,
	            @RequestParam(value = "workDate", required = false) Date workDate,
	            Model model) {

	        // 필요한 파라미터를 Map으로 묶어 전달
	        Map<String, Object> params = new HashMap<>();
	        params.put("empName", empName);
	        params.put("workDate", workDate);

	        // 기존 메서드로 조회한 출퇴근 기록 리스트
	        List<AttendanceAdminCheckDTO> attendanceList = attendanceAdminService.getAttendanceByDate(workDate.toString());

	        // 근무 상태 및 지각 여부 판단 로직 추가
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

	        // 모델에 데이터를 추가하여 뷰에 전달
	        model.addAttribute("attendanceList", attendanceList);
	        model.addAttribute("empName", empName);
	        model.addAttribute("workDate", workDate);

	        // 뷰 경로 반환 (예: /WEB-INF/views/admin/attendanceList.jsp)
	        return "admin/attendanceList";
	    }

}
