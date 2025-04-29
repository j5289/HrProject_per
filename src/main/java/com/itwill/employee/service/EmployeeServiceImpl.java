package com.itwill.employee.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itwill.employee.domain.EmployeeVO;
import com.itwill.employee.persistence.EmployeeDAO;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeDAO employeeDAO;

    @Override
    public List<EmployeeVO> getAllEmployees() {
        return employeeDAO.getAllEmployees();
    }

    @Override
    public EmployeeVO getEmployeeById(String emp_id) {
        return employeeDAO.getEmployeeById(emp_id);
    }

    @Override
    public void updateEmployeeUser(EmployeeVO employee) {
        employeeDAO.updateEmployeeUser(employee);
    }

    @Override
    public void updateEmployeeAdmin(EmployeeVO employee) {
        employeeDAO.updateEmployeeAdmin(employee);
    }
    
    @Override
    public int updateResignationDate(String empId, Date empQd) {
        return employeeDAO.updateResignationDate(empId, empQd);
    }
    
    @Override
    public void deleteEmployee(String empId) {
        employeeDAO.deleteEmployee(empId);
    }
    
    @Override
    public int countTotalEmployees() {
        return employeeDAO.countTotalEmployees();
    }

    @Override
    public int countNewEmployeesThisMonth() {
        return employeeDAO.countNewEmployeesThisMonth();
    }
    
    @Override
    public void updateQuitDate(String empId, Date empQd) {
        employeeDAO.updateQuitDate(empId, empQd);
    }
    
    
}