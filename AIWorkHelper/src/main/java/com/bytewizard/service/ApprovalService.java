package com.bytewizard.service;


import com.bytewizard.dto.request.ApprovalListRequest;
import com.bytewizard.dto.request.ApprovalRequest;
import com.bytewizard.dto.request.DisposeRequest;
import com.bytewizard.dto.response.ApprovalListResponse;
import com.bytewizard.dto.response.ApprovalResponse;

/**
 * 审批服务接口
 */
public interface ApprovalService {

    /**
     * 获取审批详情
     */
    ApprovalResponse info(String id);

    /**
     * 创建审批申请
     */
    String create(ApprovalRequest request);

    /**
     * 处理审批（通过/拒绝/撤销）
     */
    void dispose(DisposeRequest request);

    /**
     * 获取审批列表
     */
    ApprovalListResponse list(ApprovalListRequest request);
}
