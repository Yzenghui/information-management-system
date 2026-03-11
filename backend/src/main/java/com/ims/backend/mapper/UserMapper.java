package com.ims.backend.mapper;

import com.ims.backend.pojo.User;
import org.apache.ibatis.annotations.*;

/**
 * 用户数据访问接口（Mapper）。
 * 这是MyBatis框架的核心组件之一，负责定义与`user`表交互的数据库操作。
 * 注意：这是一个接口，MyBatis会在运行时为其动态生成实现。
 */
@Mapper // 标识是一个MyBatis的Mapper接口
public interface UserMapper {

    /**
     * 根据用户名精确查询用户信息。
     * 这是一个典型的“按唯一键查询”操作。
     *
     * @param username 用户名，作为查询条件
     * @return 查询到的用户对象。如果数据库中不存在该用户，则返回 {@code null}。
     *         注意：返回的User对象包含数据库中所有选中字段的值，包括加密后的密码。
     */
    @Select("SELECT id, username, password, role, status, create_time, update_time FROM user WHERE username = #{username}")
    User findByUsername(@Param("username") String username);
    // 使用 `#{}` 语法是MyBatis的参数占位符，它会被替换为预编译语句(PreparedStatement)中的 `?`，能有效防止SQL注入。
    // @Param 注解明确了方法参数在SQL语句中绑定的名称。在单参数且名字匹配时可选，但显式声明是良好实践。

    /**
     * 根据用户 ID 查询用户信息。
     *
     * @param userId 用户 ID
     * @return 用户对象，包含注册时间等信息
     */
    @Select("SELECT id, username, password, role, status, create_time, update_time FROM user WHERE id = #{id}")
    User findById(@Param("id") Integer userId);


    /**
     * 保存新用户到数据库。
     * 用于用户注册功能，将加密后的用户信息持久化存储。
     *
     * @param user 包含用户信息的User对象
     * @return 受影响的行数，正常情况下应为1
     */
    @Insert("INSERT INTO user (username, password, role, status, create_time, update_time) VALUES (#{username}, #{password}, #{role}, #{status}, #{createTime}, #{updateTime})")
    @Options(useGeneratedKeys = true,    // 启用主键回写功能
            keyProperty = "id",          // 将生成的主键值赋给对象的 id 属性
            keyColumn = "id")            // 数据库中的主键列名是 id
    int insertUser(User user);

    /**
     * 更新用户密码。
     *
     * @param user 用户对象，包含新的密码
     * @return 受影响的行数
     */
    @Update("UPDATE user SET password = #{password}, update_time = #{updateTime} WHERE id = #{id}")
    int updateUser(User user);
}