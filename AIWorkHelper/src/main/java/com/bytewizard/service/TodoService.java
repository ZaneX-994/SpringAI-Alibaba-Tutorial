package com.bytewizard.service;


import com.bytewizard.dto.request.FinishTodoRequest;
import com.bytewizard.dto.request.TodoListRequest;
import com.bytewizard.dto.request.TodoRecordRequest;
import com.bytewizard.dto.request.TodoRequest;
import com.bytewizard.dto.response.TodoInfoResponse;
import com.bytewizard.dto.response.TodoListResponse;

/**
 * 待办事项服务接口
 */
public interface TodoService {

    /**
     * 获取待办详情
     */
    TodoInfoResponse info(String id);

    /**
     * 创建待办
     */
    String create(TodoRequest request);

    /**
     * 编辑待办
     */
    void edit(TodoRequest request);

    /**
     * 删除待办
     */
    void delete(String id);

    /**
     * 完成待办
     */
    void finish(FinishTodoRequest request);

    /**
     * 创建待办操作记录
     */
    void createRecord(TodoRecordRequest request);

    /**
     * 待办列表
     */
    TodoListResponse list(TodoListRequest request);
}
