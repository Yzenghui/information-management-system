package com.ims.backend.dto.response;

import lombok.Data;

/**
 * 登录响应数据传输对象。
 * 专门用于向前端返回登录成功后的用户信息和令牌。
 */
@Data
public class LoginResponse {

    /**
     * 用户ID
     */
    private Integer id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 用户角色
     */
    private String role;

    /**
     * JWT访问令牌
     */
    private String token;
}
