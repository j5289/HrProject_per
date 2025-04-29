package com.itwill.employee.domain;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class NoticeVO {
    private int notId;
    private String notTi;
    private String notCn;
    private String notFile;
    private String empId;
    private Timestamp notWd;
    private Timestamp notRegistdate;
    private String notRegister;
    private Timestamp notModifydate;
    private String notModifier;
    private int viewCount;
}