package com.itwill.domain;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class LoginHistoryVO {
	private String empId;
	private String loginIp;
	private String loginStatus;   // ACTIVE, LOCKED 등
    private String loginResult;   // SUCCESS, FAIL
	private Timestamp loginTime;
}