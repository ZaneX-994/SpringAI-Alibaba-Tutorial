/**
 * @author: 公众号：IT杨秀才
 * @doc:后端，AI知识进阶，后端面试场景题大全：https://golangstar.cn/
 */
package com.bytewizard.controller;

import com.bytewizard.common.Result;
import com.bytewizard.dto.request.FinishTodoRequest;
import com.bytewizard.dto.request.TodoListRequest;
import com.bytewizard.dto.request.TodoRecordRequest;
import com.bytewizard.dto.request.TodoRequest;
import com.bytewizard.dto.response.TodoInfoResponse;
import com.bytewizard.dto.response.TodoListResponse;
import com.bytewizard.service.TodoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 待办事项控制器
 */
@RestController
@RequestMapping("/v1/todo")
@RequiredArgsConstructor
public class TodoController {

    private final TodoService todoService;

    /**
     * 获取待办详情
     * 对应Go: GET /v1/todo/:id
     */
    @GetMapping("/{id}")
    public Result<TodoInfoResponse> info(@PathVariable String id) {
        TodoInfoResponse response = todoService.info(id);
        return Result.ok(response);
    }

    /**
     * 创建待办
     * 对应Go: POST /v1/todo
     */
    @PostMapping
    public Result<String> create(@Valid @RequestBody TodoRequest request) {
        String id = todoService.create(request);
        return Result.ok(id);
    }

    /**
     * 编辑待办
     * 对应Go: PUT /v1/todo
     */
    @PutMapping
    public Result<Void> edit(@Valid @RequestBody TodoRequest request) {
        todoService.edit(request);
        return Result.ok();
    }

    /**
     * 删除待办
     * 对应Go: DELETE /v1/todo/:id
     */
    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable String id) {
        todoService.delete(id);
        return Result.ok();
    }

    /**
     * 完成待办
     * 对应Go: POST /v1/todo/finish
     */
    @PostMapping("/finish")
    public Result<Void> finish(@Valid @RequestBody FinishTodoRequest request) {
        todoService.finish(request);
        return Result.ok();
    }

    /**
     * 创建操作记录
     * 对应Go: POST /v1/todo/record
     */
    @PostMapping("/record")
    public Result<Void> createRecord(@Valid @RequestBody TodoRecordRequest request) {
        todoService.createRecord(request);
        return Result.ok();
    }

    /**
     * 获取待办列表
     * 对应Go: GET /v1/todo/list
     */
    @GetMapping("/list")
    public Result<TodoListResponse> list(@Valid TodoListRequest request) {
        TodoListResponse response = todoService.list(request);
        return Result.ok(response);
    }
}
