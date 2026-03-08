package com.ims.backend.service.impl;

import com.ims.backend.mapper.StudentMapper;
import com.ims.backend.pojo.Student;
import com.ims.backend.service.StudentService;
import org.springframework.stereotype.Service;

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

}

