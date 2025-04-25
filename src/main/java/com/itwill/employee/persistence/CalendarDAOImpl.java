package com.itwill.employee.persistence;

import java.util.List;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.itwill.employee.domain.CalendarVO;

@Repository
public class CalendarDAOImpl implements CalendarDAO {

    @Autowired
    private SqlSession sqlSession;

    private static final String NAMESPACE = "com.itwill.mapper.CalendarMapper";

    @Override
    public void insertCalendar(CalendarVO calendar) {
        sqlSession.insert(NAMESPACE + ".insertCalendar", calendar);
    }

    @Override
    public List<CalendarVO> getAllCalendar() {
        return sqlSession.selectList(NAMESPACE + ".getAllCalendar");
    }
}
