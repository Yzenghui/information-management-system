package com.ims.backend.service.impl;


import com.ims.backend.mapper.TeacherMapper;
import com.ims.backend.pojo.Teacher;
import com.ims.backend.service.TeacherService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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

    @Override
    public List<Teacher> findByName(String name) {
        try {
            return teacherMapper.findByName(name);
        } catch (Exception e) {
            throw new RuntimeException("查询过程中发生系统错误：" + e.getMessage());
        }
    }

    @Override
    public boolean add(Teacher teacher) {
        try {
            // 1. 验证工号是否已存在
            List<Teacher> all = teacherMapper.findAll();
            for (Teacher t : all) {
                if (t.getTeacherId().equals(teacher.getTeacherId())) {
                    throw new RuntimeException("工号已存在");
                }
            }

            // 2. 设置时间
            teacher.setCreateTime(LocalDateTime.now());
            teacher.setUpdateTime(LocalDateTime.now());

            // 3. 插入数据库
            return teacherMapper.insert(teacher) == 1;
        } catch (Exception e) {
            throw new RuntimeException("添加过程中发生错误：" + e.getMessage());
        }
    }
}
