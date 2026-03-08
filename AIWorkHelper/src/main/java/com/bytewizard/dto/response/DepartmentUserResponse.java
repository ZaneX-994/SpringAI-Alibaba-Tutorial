package com.bytewizard.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 部门用户响应
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentUserResponse {

    /**
     * 关联ID
     */
    private String id;

    /**
     * 用户ID
     * JSON字段名: user (匹配Go版本)
     */
    @JsonProperty("user")
    private String userId;

    /**
     * 部门ID
     * JSON字段名: dep (匹配Go版本)
     */
    @JsonProperty("dep")
    private String depId;

    /**
     * 用户姓名
     */
    private String userName;
}