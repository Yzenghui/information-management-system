package com.ims.backend.controller;

import com.ims.backend.pojo.Result;
import com.ims.backend.pojo.Teacher;
import com.ims.backend.service.TeacherService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 教师信息管理 API 控制器。
 * 处理所有与教师信息相关的 HTTP 请求。
 */
@RestController
@RequestMapping("/api/teacher")
public class TeacherController {

    // 业务逻辑层服务，由 Spring 容器提供实现类的实例
    private final TeacherService teacherService;

    // 采用构造器注入（与 StudentController 保持一致）
    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    /**
     * 获取所有教师列表。
     * 这是一个典型的 GET 请求处理流程：查询数据库 -> 返回 JSON。
     *
     * @return 统一格式的响应结果，包含教师列表数据
     */
    @GetMapping
    public Result<?> list() {
        try {
            // 委托 Service 层查询数据库
            List<Teacher> teachers = teacherService.findAll();

            // 使用统一成功响应模板返回数据
            return Result.success(teachers);

        } catch (RuntimeException e) {
            // 业务异常或系统异常
            return Result.error(500, e.getMessage());
        }
    }

    /**
     * 添加新教师信息。
     *
     * @param teacher 包含教师信息的请求体数据
     * @return 统一格式的响应结果
     */
    @PostMapping
    public Result<?> add(@RequestBody Teacher teacher){
        try {
            // 1. 参数校验
            if (teacher.getTeacherId() == null || teacher.getTeacherId().trim().isEmpty()) {
                return Result.error(400, "工号不能为空");
            }
            if (teacher.getName() == null || teacher.getName().trim().isEmpty()) {
                return Result.error(400, "姓名不能为空");
            }

            // 2. 调用 Service 层
            boolean addResult = teacherService.add(teacher);

            // 3. 返回结果
            if (addResult) {
                return Result.success();
            } else {
                return Result.error(500, "添加失败");
            }
        } catch (RuntimeException e) {
            return Result.error(500, e.getMessage());
        }
    }
}
