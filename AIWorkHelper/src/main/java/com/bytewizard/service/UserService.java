package com.bytewizard.service;

import com.bytewizard.dto.request.LoginRequest;
import com.bytewizard.dto.request.UserRequest;
import com.bytewizard.dto.response.LoginResponse;
import com.bytewizard.dto.response.UserResponse;

public interface UserService {

    /**
     * 用户登录
     */
    LoginResponse login(LoginRequest request);

    /**
     * 获取用户信息
     */
    UserResponse info(String id);

    /**
     * 创建用户
     */
    void create(UserRequest request);

    /**
     * 编辑用户
     */
    void edit(UserRequest request);

    /**
     * 删除用户
     */
    void delete(String id);

    /**
     * 用户列表
     */
    UserListResponse list(UserListRequest request);

    /**
     * 修改密码
     */
    void updatePassword(UpdatePasswordRequest request);

    /**
     * 初始化系统管理员用户
     */
    void initAdminUser();

    /**
     * 根据用户名获取用户ID
     */
    String getUserIdByName(String name);

}
