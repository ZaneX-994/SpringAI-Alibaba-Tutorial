package com.bytewizard.controller;

import com.bytewizard.common.Result;
import com.bytewizard.dto.request.AddDepartmentUserRequest;
import com.bytewizard.dto.request.DepartmentRequest;
import com.bytewizard.dto.request.RemoveDepartmentUserRequest;
import com.bytewizard.dto.request.SetDepartmentUsersRequest;
import com.bytewizard.dto.response.DepartmentResponse;
import com.bytewizard.dto.response.DepartmentTreeResponse;
import com.bytewizard.service.DepartmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 部门控制器
 */
@RestController
@RequestMapping("/v1/dep")
@RequiredArgsConstructor
public class DepartmentController {

    private final DepartmentService departmentService;

    /**
     * 获取部门树结构（SOA）
     * 对应Go: GET /v1/dep/soa
     */
    @GetMapping("/soa")
    public Result<DepartmentTreeResponse> soa() {
        DepartmentTreeResponse response = departmentService.soa();
        return Result.ok(response);
    }

    /**
     * 获取部门详情
     * 对应Go: GET /v1/dep/:id
     */
    @GetMapping("/{id}")
    public Result<DepartmentResponse> info(@PathVariable String id) {
        DepartmentResponse response = departmentService.info(id);
        return Result.ok(response);
    }

    /**
     * 创建部门
     * 对应Go: POST /v1/dep
     */
    @PostMapping
    public Result<Void> create(@Valid @RequestBody DepartmentRequest request) {
        departmentService.create(request);
        return Result.ok();
    }

    /**
     * 更新部门
     * 对应Go: PUT /v1/dep
     */
    @PutMapping
    public Result<Void> edit(@Valid @RequestBody DepartmentRequest request) {
        departmentService.edit(request);
        return Result.ok();
    }

    /**
     * 删除部门
     * 对应Go: DELETE /v1/dep/:id
     */
    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable String id) {
        departmentService.delete(id);
        return Result.ok();
    }

    /**
     * 设置部门用户
     * 对应Go: POST /v1/dep/user
     */
    @PostMapping("/user")
    public Result<Void> setDepartmentUsers(@Valid @RequestBody SetDepartmentUsersRequest request) {
        departmentService.setDepartmentUsers(request);
        return Result.ok();
    }

    /**
     * 添加部门员工（级联到上级部门）
     * 对应Go: POST /v1/dep/user/add
     */
    @PostMapping("/user/add")
    public Result<Void> addDepartmentUser(@Valid @RequestBody AddDepartmentUserRequest request) {
        departmentService.addDepartmentUser(request);
        return Result.ok();
    }

    /**
     * 删除部门员工（级联从上级部门删除）
     * 对应Go: DELETE /v1/dep/user/remove
     */
    @DeleteMapping("/user/remove")
    public Result<Void> removeDepartmentUser(@Valid @RequestBody RemoveDepartmentUserRequest request) {
        departmentService.removeDepartmentUser(request);
        return Result.ok();
    }

    /**
     * 获取用户部门信息（包含完整的上级部门层级）
     * 对应Go: GET /v1/dep/user/:id
     */
    @GetMapping("/user/{id}")
    public Result<DepartmentResponse> departmentUserInfo(@PathVariable String id) {
        DepartmentResponse response = departmentService.departmentUserInfo(id);
        return Result.ok(response);
    }
}