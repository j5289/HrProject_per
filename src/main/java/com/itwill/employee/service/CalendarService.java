package com.itwill.employee.service;

import java.util.List;

import com.itwill.employee.domain.CalendarVO;

public interface CalendarService {
    void register(CalendarVO calendar);
    List<CalendarVO> getCalendarList();
}
