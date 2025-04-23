package com.itwill.employee.domain;

import java.sql.Date;
import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CalendarVO {
    private int calId;
    private String calTitle;
    private Date calDate;
    private String calWriter;
    private Timestamp calRegistdate;
}
