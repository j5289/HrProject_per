package com.itwill.attendance.dto;

import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 2. 사원의 지각 현황 및 조회 DTO
 *
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AttendanceLateDTO {

	private String empId; //사원 번호
	private String workDate; //근무 날짜(단일 날짜 or 가장 최근 날짜)
	
	private Integer latenessMinutes; //지각 시간(분)
	private String reason; //지각 이유 
	private Boolean isLate; //지각 여부, DB에 없음 
	
	private String managerCheck; //관리자 확인 여부(확인/미확인), DB에 없음
	
	//지각 사유서 파일 정보 
	private String fimeName; //지각 사유서 제목
	private String filePath; //지각 사유서 경로 
	private String fileSize; //지각 사유서 크기
	private String fileType; //파일 유형 
	
	//기간 조회 시만 사용(옵션, 둘 다 DB에 없음)
	private Integer totalLateCount; // 지각 횟수
    private Integer totalLateMinutes; // 총 지각 시간
	
}
