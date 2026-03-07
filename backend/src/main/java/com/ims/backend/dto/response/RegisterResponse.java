package com.ims.backend.dto.response;

import lombok.Data;

/**
 * 用户注册响应数据传输对象。
 * 专门用于向前端返回注册成功后的用户信息和认证令牌。
 */
@Data
public class RegisterResponse {

    /**
     * 用户唯一标识符。
     * 数据库中的主键ID，用于后续API调用的身份识别。
     */
    private Integer id;

    /**
     * 用户名。
     * 注册时提供的用户名，用于显示和身份标识。
     */
    private String username;

    /**
     * 用户角色。
     * 默认为"USER"，用于权限控制和功能访问限制。
     */
    private String role;

    /**
     * JWT访问令牌。
     * 注册成功后立即生成的认证令牌，前端需在后续请求中携带。
     * 有效期遵循application.yml中的配置。
     */
    private String token;
}
