package com.ims.backend.mapper;

import com.ims.backend.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

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
    @Select("SELECT id, username, password, role, status FROM user WHERE username = #{username}")
    User findByUsername(@Param("username") String username);
    // 使用 `#{}` 语法是MyBatis的参数占位符，它会被替换为预编译语句(PreparedStatement)中的 `?`，能有效防止SQL注入。
    // @Param 注解明确了方法参数在SQL语句中绑定的名称。在单参数且名字匹配时可选，但显式声明是良好实践。
}