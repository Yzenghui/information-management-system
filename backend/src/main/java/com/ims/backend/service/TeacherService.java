package com.ims.backend.service;


import com.ims.backend.pojo.Teacher;

import java.util.List;

/**
 * 教师信息管理业务接口。
 * 定义了教师信息管理相关的业务逻辑方法。
 */
public interface TeacherService {

    /**
     * 获取所有教师列表。
     *
     * @return 统一格式的响应结果，包含教师列表数据
     */
    List<Teacher> findAll();

    /**
     * 根据姓名模糊查询教师信息。
     *
     * @param name 教师姓名
     * @return 统一格式的响应结果，包含查询到的教师列表数据
     */
    List findByName(String name);
}
