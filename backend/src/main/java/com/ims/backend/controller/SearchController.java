package com.ims.backend.controller;

import com.ims.backend.dto.response.SearchResponse;
import com.ims.backend.pojo.Result;
import com.ims.backend.pojo.Student;
import com.ims.backend.pojo.Teacher;
import com.ims.backend.service.StudentService;
import com.ims.backend.service.TeacherService;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 统一搜索控制器。
 * 同时查询学生和教师，返回统一格式的结果。
 */
@RestController
@RequestMapping("/api/search")
public class SearchController {

    private final StudentService studentService;
    private final TeacherService teacherService;

    public SearchController(StudentService studentService, TeacherService teacherService) {
        this.studentService = studentService;
        this.teacherService = teacherService;
    }

    /**
     * 根据姓名搜索学生和教师。
     *
     * @param name 要搜索的姓名
     * @return 统一格式的学生和教师列表
     */
    @GetMapping
    public Result<?> search(@RequestParam String name) {
        try {
            // 1. 同时查询学生和教师
            List<Student> students = studentService.findByName(name);
            List<Teacher> teachers = teacherService.findByName(name);

            // 2. 转换为统一格式
            List<SearchResponse> results = new ArrayList<>();

            // 转换学生数据
            for (Student student : students) {
                SearchResponse response = new SearchResponse();
                response.setId(student.getStudentId());  // 学号
                response.setName(student.getName());
                response.setGender(student.getGender());
                response.setProfession("学生");
                response.setCourse(student.getMajor());  // 专业
                response.setAddress(student.getAddress());
                results.add(response);
            }

            // 转换教师数据
            for (Teacher teacher : teachers) {
                SearchResponse response = new SearchResponse();
                response.setId(teacher.getTeacherId());  // 工号
                response.setName(teacher.getName());
                response.setGender(teacher.getGender());
                response.setProfession("教师");
                response.setCourse(teacher.getSubject());  // 学科
                response.setAddress(teacher.getAddress());
                results.add(response);
            }

            // 3. 返回结果
            return Result.success(results);

        } catch (RuntimeException e) {
            return Result.error(500, "搜索失败：" + e.getMessage());
        }
    }
}
