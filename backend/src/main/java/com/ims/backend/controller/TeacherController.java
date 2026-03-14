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
        // 委托 Service 层查询数据库
        List<Teacher> teachers = teacherService.findAll();

        // 使用统一成功响应模板返回数据
        return Result.success(teachers);

    }

    /**
     * 添加新教师信息。
     *
     * @param teacher 包含教师信息的请求体数据
     * @return 统一格式的响应结果
     */
    @PostMapping
    public Result<?> add(@RequestBody Teacher teacher) {
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
    }

    /**
     * 根据工号删除教师信息。
     *
     * @param teacherId 工号
     * @return 统一格式的响应结果
     */
    @DeleteMapping("/{teacherId}")
    public Result<?> delete(@PathVariable String teacherId) {
        if (teacherId == null || teacherId.trim().isEmpty()) {
            return Result.error(400, "工号不能为空");
        }
        boolean success = teacherService.deleteByTeacherId(teacherId);
        if (success) {
            return Result.success();
        } else {
            return Result.error(500, "删除失败，未找到该记录");
        }
    }
}
