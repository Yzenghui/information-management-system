package com.ims.backend.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * 用户注册数据传输对象 (Data Transfer Object)。
 * 专门用于接收前端注册请求的JSON数据。
 * 设计原则：
 * 1. 分层验证：使用不同注解分别处理长度、格式、必填等验证
 * 2. 安全性：严格的密码强度要求和用户名格式限制
 * 3. 用户体验：提供清晰的错误提示信息
 */
@Data
public class RegisterRequest {

    /**
     * 用户名字段。
     * 验证规则：
     * - 非空验证：不能为空或纯空格
     * - 长度限制：3-20个字符（与前端保持一致）
     * - 格式限制：只能包含字母、数字和下划线
     */
    @NotBlank(message = "用户名不能为空")
    @Size(min = 3, max = 20, message = "用户名长度必须在3到20个字符之间")
    @Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "用户名只能包含字母、数字和下划线")
    private String username;

    /**
     * 密码字段（明文传输）。
     * 验证规则：
     * - 非空验证：不能为空或纯空格
     * - 长度限制：6-20个字符（与前端保持一致）
     * - 复杂度要求：必须同时包含字母和数字
     */
    @NotBlank(message = "密码不能为空")
    @Size(min = 6, max = 20, message = "密码长度必须在6到20个字符之间")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d).*$",
            message = "密码必须包含字母和数字")
    private String password;

    /**
     * 用户角色。
     * 可选值：STUDENT（学生）、TEACHER（教师）、ADMIN（管理员）
     * 默认：STUDENT
     */
    private String role;
}
