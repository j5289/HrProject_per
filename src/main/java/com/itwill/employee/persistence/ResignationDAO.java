package com.itwill.employee.persistence;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.itwill.employee.domain.ResignationVO;

public interface ResignationDAO {
	    void insertResignation(ResignationVO vo);
	    List<ResignationVO> getAllResignations();
	    ResignationVO getResignationById(int resignId);
	    void updateResignationStatus(ResignationVO vo); // ✔ 최종 승인/반려용 하나만 유지
	

}
