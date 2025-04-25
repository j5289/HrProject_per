package com.itwill.attendance.dto;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Data;

/**
 * 2.관리자의 사원별 휴가 일수 조회 DTO
 *
 */

@Data
@Builder
public class AttendanceAdminLeaveDTO {
	
	private String empId; //사원 id
	private String empName; //사원 이름(DB조인용)
	private String depName; //부서 이름
	private String leaveId; //휴가 id
	private String managerId; //관리자 id, DB에 없음
	
	private LocalDate uploadDate; //휴가 신청서 제출 날짜
	private LocalDate leaveStartDate; //휴가 시작일
	private LocalDate leaveEndDate; //휴가 종료일
	
	private String leaveStatus; //휴가 종류(연차, 병가 등)
	private Integer leaveDays; //휴가 일수(계산해줌)
	private String approvalConfirmed; //승인 결과 (승인, 반려, 대기)
	
	// === 조회 조건 ===
	private LocalDate date; //단일 조회 날짜(년/월/일)
	private LocalDate startDate; //조회 시작 날짜 (DB에 없음)
	private LocalDate endDate; //조회 종료 날짜 (DB에 없음)

	private String fileName; //보고서 이름 
	private String filePath;//보고서 파일 경로
	private Long fileSize; //보고서 파일 크기
	private String fileType; //보고서 파일 형식 
	
	private String leaveSummary; //출력용 휴가 요약 정보(DB에 없음)
	//예 : "2024-05-01 ~ 2024-05-03 (3일) / 연차 / 승인"
	

}
