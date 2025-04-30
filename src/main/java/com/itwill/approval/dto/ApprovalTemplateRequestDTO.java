package com.itwill.approval.dto;

import java.util.List;

import lombok.Data;

@Data
public class ApprovalTemplateRequestDTO {
    private String templateId;
    private String templateName;
    private String ownerId;
    private List<ApprovalLineDetailDTO> detailList;
}