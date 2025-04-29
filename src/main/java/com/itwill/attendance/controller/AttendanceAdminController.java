package com.itwill.attendance.controller;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.itwill.attendance.dto.AttendanceAdminCheckDTO;
import com.itwill.attendance.dto.AttendanceAdminLateDTO;
import com.itwill.attendance.dto.AttendanceAdminLeaveDTO;
import com.itwill.attendance.dto.AttendanceAdminUpdateAndDeleteDTO;
import com.itwill.attendance.dto.AttendanceAdminWorkDTO;
import com.itwill.attendance.service.AttendanceAdminService;

/**
 * 관리자용 컨트롤러 
 *
 */

@Controller
@RequestMapping("/attendance")
public class AttendanceAdminController{
	
		@Autowired
		private AttendanceAdminService attendanceAdminService;
	
		// ===== 1. 관리자의 사원 출퇴근 기록 및 현황 조회 =====
		// 전체 사원의 출퇴근 기록 리스트 조회(조건 : 이름, 날짜)
		@GetMapping("/admin_attendance")
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
		@RequestMapping("/admin_leave_check")
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
		
		// ===== 3. 관리자의 사원 지각 조회 =====
		 // 관리자별 사원 지각 현황 조회
		@GetMapping("/late-list")
		public String getAdminLateStatusList(
		        @RequestParam(value = "empName", required = false) String empName,
		        @RequestParam(value = "startDate", required = false) String startDate,
		        @RequestParam(value = "endDate", required = false) String endDate,
		        Model model) {

		    Map<String, Object> params = new HashMap<>();
		    params.put("empName", empName);
		    params.put("startDate", startDate);
		    params.put("endDate", endDate);

		    // 관리자별 사원 지각 현황 조회
		    List<AttendanceAdminLateDTO> lateList = attendanceAdminService.getLateStatusByAdmin(params);
		    model.addAttribute("lateList", lateList);

		    // JSP 경로를 "attendance/admin_lateness_check.jsp"로 수정
		    return "attendance/admin_lateness_check"; // JSP 템플릿

		}
		
		// ===== 4. 관리자의 사원 근무 조회 및 근무 입력 =====
		// 관리자 근무 목록 조회
	    @RequestMapping("/work-list")
	    public String showWorkList(Model model) {
	        List<AttendanceAdminWorkDTO> workList = attendanceAdminService.getWorkStatusByAdmin(new HashMap<>());
	        model.addAttribute("workList", workList);
	        return "attendance/admin_work_list";
	    }

	    // 관리자 근무 상세 조회 폼 이동
	    @GetMapping("/work-update-form/{empId}/{workDate}")
	    public String showWorkUpdateForm(
	            @PathVariable String empId,
	            @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date workDate,
	            Model model) {
	        AttendanceAdminUpdateAndDeleteDTO dto = attendanceAdminService.getWorkDetail(empId, workDate);
	        model.addAttribute("work", dto);
	        return "attendance/admin_work_update_form";
	    }

	    // 근무 수정 처리
	    @PostMapping("/work-update")
	    public String updateWorkStatus(@ModelAttribute AttendanceAdminUpdateAndDeleteDTO dto, Model model) {
	        boolean success = attendanceAdminService.updateWorkStatus(dto);
	        if (success) {
	            model.addAttribute("successMessage", "수정이 완료되었습니다.");
	        } else {
	            model.addAttribute("errorMessage", "수정에 실패했습니다.");
	        }
	        return "redirect:/admin/work-list";
	    }

	    // 근무 삭제 처리
	    @PostMapping("/work-delete")
	    public String deleteWorkStatus(@ModelAttribute AttendanceAdminUpdateAndDeleteDTO dto, Model model) {
	        boolean success = attendanceAdminService.deleteWorkStatus(dto);
	        if (success) {
	            model.addAttribute("successMessage", "삭제가 완료되었습니다.");
	        } else {
	            model.addAttribute("errorMessage", "삭제에 실패했습니다.");
	        }
	        return "redirect:/admin/work-list";
	    }

	    // 근무 등록 처리
	    @PostMapping("/work-insert")
	    public String insertWorkStatus(@ModelAttribute AttendanceAdminWorkDTO dto, Model model) {
	        boolean success = attendanceAdminService.insertWorkStatus(dto);
	        if (success) {
	            model.addAttribute("successMessage", "등록이 완료되었습니다.");
	        } else {
	            model.addAttribute("errorMessage", "등록에 실패했습니다.");
	        }
	        return "redirect:/admin/work-list";
	    }


 

}