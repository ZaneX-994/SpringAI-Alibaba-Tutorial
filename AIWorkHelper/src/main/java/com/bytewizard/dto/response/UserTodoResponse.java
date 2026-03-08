/**
 * @author: 公众号：IT杨秀才
 * @doc:后端，AI知识进阶，后端面试场景题大全：https://golangstar.cn/
 */
package com.bytewizard.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户待办响应
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserTodoResponse {

    /**
     * ID
     */
    private String id;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 用户姓名
     */
    private String userName;

    /**
     * 待办ID
     */
    private String todoId;

    /**
     * 待办状态
     */
    private Integer todoStatus;
}
