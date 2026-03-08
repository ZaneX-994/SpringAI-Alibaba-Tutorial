package com.bytewizard.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * 后端，AI知识进阶，后端面试场景题大全：https://golangstar.cn/
 * 部门用户关联实体类
 */
@Data
@Document(collection = "department_user")
public class DepartmentUser {

    @Id
    private String id;

    /**
     * 部门ID
     */
    @Field("depId")
    private String depId;

    /**
     * 用户ID
     */
    @Field("userId")
    private String userId;

    /**
     * 更新时间
     */
    @Field("updateAt")
    private Long updateAt;

    /**
     * 创建时间
     */
    @Field("createAt")
    private Long createAt;
}