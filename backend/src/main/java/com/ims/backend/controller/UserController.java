package com.ims.backend.controller;

import com.ims.backend.pojo.Result;
import com.ims.backend.pojo.User;
import com.ims.backend.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

/**
 * 用户个人中心控制器。
 * 处理个人信息查询和密码修改请求。
 */
@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * 获取当前登录用户的个人信息。
     * 从 Spring Security 上下文中自动获取当前用户，并查询数据库获取完整信息。
     *
     * @return 统一格式的响应结果：
     * - username: 用户名
     * - registerTime: 注册时间
     */
    @GetMapping("/profile")
    public Result<?> getProfile() {
        // 1. 从 SecurityContext 获取当前登录用户名
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // 2. 查询数据库获取用户完整信息
        User user = userService.findByUsername(username);

        if (user == null) {
            return Result.error(404, "用户不存在");
        }

        // 3. 构建返回数据
        Map<String, Object> profile = new HashMap<>();
        profile.put("username", user.getUsername());
        // 使用自定义格式：yyyy-MM-dd HH:mm:ss
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        profile.put("registerTime", user.getCreateTime().format(formatter));

        return Result.success(profile);
    }

    /**
     * 修改当前登录用户的密码。
     *
     * @param request 包含原密码和新密码的请求体
     *                - oldPassword: 原密码
     *                - newPassword: 新密码
     *                - confirmPassword: 确认密码
     * @return 统一格式的响应结果
     */
    @PutMapping("/password")
    public Result<?> changePassword(@RequestBody Map<String, String> request) {
        // 1. 参数校验
        String oldPassword = request.get("oldPassword");
        String newPassword = request.get("newPassword");
        String confirmPassword = request.get("confirmPassword");

        if (oldPassword == null || oldPassword.trim().isEmpty()) {
            return Result.error(400, "原密码不能为空");
        }
        if (newPassword == null || newPassword.trim().isEmpty()) {
            return Result.error(400, "新密码不能为空");
        }
        if (!newPassword.equals(confirmPassword)) {
            return Result.error(400, "两次输入的密码不一致");
        }
        if (newPassword.length() < 6 || newPassword.length() > 20) {
            return Result.error(400, "密码长度必须在 6-20 个字符之间");
        }
        if (!newPassword.matches("^(?=.*[A-Za-z])(?=.*\\d).*$")) {
            return Result.error(400, "密码必须包含字母和数字");
        }

        // 2. 获取当前用户
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.findByUsername(username);

        if (user == null) {
            return Result.error(404, "用户不存在");
        }

        // 3. 调用 Service 层修改密码
        boolean success = userService.changePassword(user.getId(), oldPassword, newPassword);

        if (success) {
            return Result.success();
        } else {
            return Result.error(400, "原密码错误");
        }
    }
}
