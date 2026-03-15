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

    /**
     * 添加学生信息。
     *
     * @param student 学生对象，包含待插入的学生信息
     * @return true 表示添加成功，false 表示添加失败
     */
    boolean add(Student student);

    /**
     * 根据学号删除学生信息。
     *
     * @param studentId 学号
     * @return true 表示删除成功，false 表示删除失败（未找到记录）
     */
    boolean deleteByStudentId(String studentId);

}
