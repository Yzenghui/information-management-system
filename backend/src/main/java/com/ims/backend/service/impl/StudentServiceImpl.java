package com.ims.backend.service.impl;

import com.ims.backend.mapper.StudentMapper;
import com.ims.backend.pojo.Student;
import com.ims.backend.service.StudentService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 学生信息服务实现类。
 * 使用构造器注入，处理业务逻辑和异常。
 */
@Service
public class StudentServiceImpl implements StudentService {

    private final StudentMapper studentMapper;

    // 构造器注入
    public StudentServiceImpl(StudentMapper studentMapper) {
        this.studentMapper = studentMapper;
    }

    @Override
    public List<Student> findAll() {
        try {
            return studentMapper.findAll();
        } catch (Exception e) {
            throw new RuntimeException("查询过程中发生系统错误：" + e.getMessage());
        }
    }

    @Override
    public List<Student> findByName(String name) {
        try {
            return studentMapper.findByName(name);
        } catch (Exception e) {
            throw new RuntimeException("查询过程中发生系统错误：" + e.getMessage());
        }
    }
    @Override
    public boolean add(Student student) {
        try {
            // 1. 验证学号是否已存在
            List<Student> all = studentMapper.findAll();
            for (Student s : all) {
                if (s.getStudentId().equals(student.getStudentId())) {
                    throw new RuntimeException("学号已存在");
                }
            }

            // 2. 设置时间
            student.setCreateTime(LocalDateTime.now());
            student.setUpdateTime(LocalDateTime.now());

            // 3. 插入数据库
            return studentMapper.insert(student) == 1;
        } catch (Exception e) {
            throw new RuntimeException("添加过程中发生错误：" + e.getMessage());
        }
    }


}

