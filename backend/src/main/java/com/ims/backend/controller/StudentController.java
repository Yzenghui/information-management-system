package com.ims.backend.controller;

import com.ims.backend.pojo.Result;
import com.ims.backend.pojo.Student;
import com.ims.backend.service.StudentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 学生信息管理 API 控制器。
 * 处理所有与学生信息相关的 HTTP 请求。
 */
@RestController
@RequestMapping("/api/student")
public class StudentController {

    // 业务逻辑层服务，由 Spring 容器提供实现类的实例
    private final StudentService studentService;

    // 采用构造器注入（与 AuthController 保持一致）
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    /**
     * 获取所有学生列表。
     * 这是一个典型的 GET 请求处理流程：查询数据库 -> 返回 JSON。
     *
     * @return 统一格式的响应结果，包含学生列表数据
     */
    @GetMapping
    public Result<?> list() {
        try {
            // 委托 Service 层查询数据库
            List<Student> students = studentService.findAll();

            // 使用统一成功响应模板返回数据
            return Result.success(students);

        } catch (RuntimeException e) {
            // 业务异常或系统异常
            return Result.error(500, e.getMessage());
        }
    }

    /**
     * 添加新学生信息。
     *
     * @param student 包含学生信息的请求体数据
     * @return 统一格式的响应结果
     */
    @PostMapping
    public Result<?> add(@RequestBody Student student) {
        try {
            if (student.getStudentId() == null || student.getStudentId().trim().isEmpty()) {
                return Result.error(400, "学号不能为空");
            }
            if (student.getName() == null || student.getName().trim().isEmpty()) {
                return Result.error(400, "姓名不能为空");
            }

            boolean addResult = studentService.add(student);

            if (addResult) {
                return Result.success();
            } else {
                return Result.error(500, "添加失败");
            }
        } catch (RuntimeException e) {
            return Result.error(500, e.getMessage());
        }
    }

    /**
     * 根据学号删除学生信息。
     *
     * @param studentId 学号
     * @return 统一格式的响应结果
     */
    @DeleteMapping("/{studentId}")
    public Result<?> delete(@PathVariable String studentId) {
        try {
            if (studentId == null || studentId.trim().isEmpty()) {
                return Result.error(400, "学号不能为空");
            }
            boolean success = studentService.deleteByStudentId(studentId);
            if (success) {
                return Result.success();
            } else {
                return Result.error(500, "删除失败，未找到该记录");
            }
        } catch (RuntimeException e) {
            return Result.error(500, e.getMessage());
        }
    }
}
