package com.ims.backend.service;

import com.ims.backend.pojo.User;

/**
 * 用户服务接口。
 * 定义用户管理相关的业务逻辑方法。
 */
public interface UserService {

    /**
     * 根据用户名查询用户信息。
     *
     * @param username 用户名
     * @return 用户对象，如果不存在则返回 null
     */
    User findByUsername(String username);

    /**
     * 根据用户 ID 查询用户信息。
     *
     * @param userId 用户 ID
     * @return 用户对象，如果不存在则返回 null
     */
    User findById(Integer userId);

    /**
     * 验证原密码并更新新密码。
     *
     * @param userId      用户 ID
     * @param oldPassword 原密码（明文）
     * @param newPassword 新密码（明文）
     * @return true 表示修改成功，false 表示原密码错误
     * @throws RuntimeException 当用户不存在或数据库操作失败时抛出
     */
    boolean changePassword(Integer userId, String oldPassword, String newPassword);
}
