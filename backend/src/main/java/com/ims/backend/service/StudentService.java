package com.ims.backend.service;

import com.ims.backend.pojo.Student;
import java.util.List;

/**
 * 学生信息服务接口。
 * 定义学生管理的业务能力契约。
 */
public interface StudentService {

    /**
     * 查询所有学生信息。
     *
     * @return 学生列表
     */
    List<Student> findAll();

    /**
     * 根据姓名模糊查询学生信息。
     *
     * @param name 学生姓名
     * @return 符合条件的学生列表，如果未找到则返回空列表
     */
    List<Student> findByName(String name);

}
