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
    List<Teacher> findByName(String name);

    /**
     * 添加教师信息。
     *
     * @param teacher 教师对象，包含待插入的教师信息
     * @return true 表示添加成功，false 表示添加失败
     */
    boolean add(Teacher teacher);

    /**
     * 根据工号删除教师信息。
     *
     * @param teacherId 工号
     * @return true 表示删除成功，false 表示删除失败（未找到记录）
     */
    boolean deleteByTeacherId(String teacherId);

}
