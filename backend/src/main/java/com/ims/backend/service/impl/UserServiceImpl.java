package com.ims.backend.service.impl;

import com.ims.backend.mapper.UserMapper;
import com.ims.backend.pojo.User;
import com.ims.backend.service.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * 用户服务实现类。
 * 处理用户相关的业务逻辑和密码加密验证。
 */
@Service
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserServiceImpl(UserMapper userMapper, BCryptPasswordEncoder passwordEncoder) {
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User findByUsername(String username) {
        try {
            return userMapper.findByUsername(username);
        } catch (Exception e) {
            throw new RuntimeException("查询用户信息失败：" + e.getMessage());
        }
    }

    @Override
    public User findById(Integer userId) {
        try {
            return userMapper.findById(userId);
        } catch (Exception e) {
            throw new RuntimeException("查询用户信息失败：" + e.getMessage());
        }
    }

    @Override
    public boolean changePassword(Integer userId, String oldPassword, String newPassword) {
        try {
            // 1. 查询用户信息
            User user = userMapper.findById(userId);
            if (user == null) {
                throw new RuntimeException("用户不存在");
            }

            // 2. 验证原密码（BCrypt 比对）
            if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
                return false;  // 原密码错误
            }

            // 3. 加密新密码
            String encodedPassword = passwordEncoder.encode(newPassword);

            // 4. 更新密码和更新时间
            user.setPassword(encodedPassword);
            user.setUpdateTime(LocalDateTime.now());

            // 5. 保存到数据库
            return userMapper.updateUser(user) == 1;
        } catch (Exception e) {
            throw new RuntimeException("修改密码失败：" + e.getMessage());
        }
    }
}
