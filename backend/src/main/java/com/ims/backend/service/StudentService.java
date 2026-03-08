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

}
