package com.itwill.employee.persistence;

import java.util.List;
import com.itwill.employee.domain.CalendarVO;

public interface CalendarDAO {
    void insertCalendar(CalendarVO calendar);
    List<CalendarVO> getAllCalendar();
}
