package com.itwill.employee.persistence;

import java.util.List;
import java.util.Map;

import com.itwill.employee.domain.DepartmentVO;
import com.itwill.employee.domain.EmployeeVO;

public interface DepartmentDAO {

    // 전체 부서 목록 조회
    List<DepartmentVO> getAllDepartments();

    // 부서 등록
    boolean addDepartment(DepartmentVO department);

    // 부서 수정
    boolean updateDepartment(DepartmentVO department);

    // 부서 삭제
    boolean deleteDepartment(String deptId);

    // 부서별 사원 목록 조회
    List<EmployeeVO> getEmployeesByDeptId(String deptId);
    
    // 부서별 인원 현황
    List<Map<String, Object>> getDepartmentEmployeeCounts();

    
    
}
