package com.ims.backend.dto;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;

/**
 * 登录数据传输对象 (Data Transfer Object)。
 * 专门用于接收前端登录请求的JSON数据。
 *
 * DTO的核心价值：
 * 1. 安全性：只暴露接口必需的字段，防止数据库实体过度暴露。
 * 2. 解耦性：前端请求格式与后端数据库实体结构解耦，独立变化。
 * 3. 验证聚焦：可以针对接口需求定制验证规则，与持久化层的验证分离。
 */
@Data // Lombok注解：编译时自动生成getter、setter、toString等方法
public class LoginDTO {

    /**
     * 用户名。
     * 使用 {@code @NotBlank} 进行声明式验证，确保字符串非null且至少包含一个非空白字符。
     * 验证失败时，将返回指定的错误信息。
     */
    @NotBlank(message = "用户名不能为空")
    private String username;

    /**
     * 密码（明文，由前端传输）。
     * 注意：此处接收的是明文密码，在服务端会使用BCrypt进行验证。
     * 同样进行非空验证。
     */
    @NotBlank(message = "密码不能为空")
    private String password;
}