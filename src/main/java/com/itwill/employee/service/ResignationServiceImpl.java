package com.itwill.employee.service;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itwill.employee.domain.ResignationVO;
import com.itwill.employee.persistence.EmployeeDAO;
import com.itwill.employee.persistence.ResignationDAO;

@Service
public class ResignationServiceImpl implements ResignationService {

    @Autowired
    private ResignationDAO resignationDAO;
    
    @Autowired
    private EmployeeDAO employeeDAO;

    @Override
    public void insertResignation(ResignationVO vo) {
        resignationDAO.insertResignation(vo);
    }

    @Override
    public List<ResignationVO> getAllResignations() {
        return resignationDAO.getAllResignations();
    }

    @Override
    public ResignationVO getResignationById(int resignId) {
        return resignationDAO.getResignationById(resignId);
    }

    @Override
    public void updateResignationStatus(ResignationVO vo) {
        // 현재 신청 정보 가져오기
        ResignationVO current = resignationDAO.getResignationById(vo.getResignId());

        // 처리된 상태면 이중 방어
        if (!"대기".equals(current.getStatus())) {
            throw new IllegalStateException("이미 처리된 신청입니다.");
        }

        vo.setApprover(vo.getApprover());

        if ("승인".equals(vo.getStatus())) {
            // 승인일 설정
            vo.setApproveDate(new Timestamp(System.currentTimeMillis()));

            // 퇴사일 DB 반영
            employeeDAO.updateQuitDate(current.getEmpId(), current.getResignationDate());
        }

        resignationDAO.updateResignationStatus(vo);
    }

}
