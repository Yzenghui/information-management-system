package com.ims.backend.service.impl;


import com.ims.backend.mapper.TeacherMapper;
import com.ims.backend.pojo.Teacher;
import com.ims.backend.service.TeacherService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 教师信息管理服务实现类。
 * 这是一个接口实现类，用于提供教师信息管理相关的业务逻辑实现。
 */
@Service
public class TeacherServiceImpl implements TeacherService {

    private final TeacherMapper teacherMapper;

    // 构造器注入Mapper实例
    public TeacherServiceImpl(TeacherMapper teacherMapper) {
        this.teacherMapper = teacherMapper;
    }

    @Override
    public List<Teacher> findAll() {
        try {
            return teacherMapper.findAll();
        } catch (Exception e) {
            throw new RuntimeException("查询过程中发生系统错误：" + e.getMessage());
        }
    }
}
