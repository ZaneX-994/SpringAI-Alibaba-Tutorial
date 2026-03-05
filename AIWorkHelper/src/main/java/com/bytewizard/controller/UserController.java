package com.bytewizard.controller;

import com.bytewizard.common.Result;
import com.bytewizard.dto.request.LoginRequest;
import com.bytewizard.dto.request.UpdatePasswordRequest;
import com.bytewizard.dto.request.UserListRequest;
import com.bytewizard.dto.request.UserRequest;
import com.bytewizard.dto.response.LoginResponse;
import com.bytewizard.dto.response.UserListResponse;
import com.bytewizard.dto.response.UserResponse;
import com.bytewizard.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * 创建用户
     * POST /v1/user
     */
    @PostMapping
    public Result<Void> create(@Valid @RequestBody UserRequest request) {
        System.out.println("test_create");
        userService.create(request);
        return Result.ok();
    }

    /**
     * 用户登录
     * POST /v1/user/login
     */
    @PostMapping("/login")
    public Result<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        System.out.println("test_login");
        LoginResponse response = userService.login(request);
        return Result.ok(response);
    }

    /**
     * 获取用户信息
     * GET /v1/user/{id}
     */
    @GetMapping("/{id}")
    public Result<UserResponse> info(@PathVariable(name = "id") String id) {
        System.out.println("test_info: " + id);
        UserResponse response = userService.info(id);
        return Result.ok(response);
    }

    /**
     * 编辑用户
     * PUT /v1/user
     */
    @PutMapping
    public Result<Void> edit(@Valid @RequestBody UserRequest request) {
        userService.edit(request);
        return Result.ok();
    }

    /**
     * 删除用户
     * DELETE /v1/user/{id}
     */
    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable String id) {
        userService.delete(id);
        return Result.ok();
    }

    /**
     * 用户列表
     * GET /v1/user/list
     */
    @GetMapping("/list")
    public Result<UserListResponse> list(UserListRequest request) {
        UserListResponse response = userService.list(request);
        return Result.ok(response);
    }

    /**
     * 修改密码
     * POST /v1/user/password
     */
    @PostMapping("/password")
    public Result<Void> updatePassword(@Valid @RequestBody UpdatePasswordRequest request) {
        userService.updatePassword(request);
        return Result.ok();
    }


}
