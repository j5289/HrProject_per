package com.itwill.approval.dto;

import java.util.List;

import lombok.Data;

@Data
public class ApprovalLineTemplateListDTO {
    private String templateId;
    private String templateName;

    // 템플릿에 포함된 결재자 상세 목록
    private List<ApprovalLineDetailDTO> approvers;
}
