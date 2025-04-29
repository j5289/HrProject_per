package com.itwill.employee.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itwill.employee.domain.CalendarVO;
import com.itwill.employee.persistence.CalendarDAO;

@Service
public class CalendarServiceImpl implements CalendarService {

    @Autowired
    private CalendarDAO calendarDAO;

    @Override
    public void register(CalendarVO calendar) {
        calendarDAO.insertCalendar(calendar);
    }

    @Override
    public List<CalendarVO> getCalendarList() {
        return calendarDAO.getAllCalendar();
    }
}
