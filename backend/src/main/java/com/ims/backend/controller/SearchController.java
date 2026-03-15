package com.ims.backend.controller;

import com.ims.backend.dto.response.SearchResponse;
import com.ims.backend.pojo.Result;
import com.ims.backend.pojo.Student;
import com.ims.backend.pojo.Teacher;
import com.ims.backend.service.StudentService;
import com.ims.backend.service.TeacherService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String role = authentication.getAuthorities().stream()
                .findFirst()
                .map(auth -> auth.getAuthority())
                .orElse("");

        List<Student> students;
        List<Teacher> teachers = new ArrayList<>();

        if ("ROLE_STUDENT".equals(role)) {
            students = studentService.findByName(name);
        } else {
            students = studentService.findByName(name);
            teachers = teacherService.findByName(name);
        }

        List<SearchResponse> results = new ArrayList<>();

        for (Student student : students) {
            SearchResponse response = new SearchResponse();
            response.setId(student.getStudentId());
            response.setName(student.getName());
            response.setGender(student.getGender());
            response.setProfession("学生");
            response.setCourse(student.getMajor());
            response.setAddress(student.getAddress());
            results.add(response);
        }

        for (Teacher teacher : teachers) {
            SearchResponse response = new SearchResponse();
            response.setId(teacher.getTeacherId());
            response.setName(teacher.getName());
            response.setGender(teacher.getGender());
            response.setProfession("教师");
            response.setCourse(teacher.getSubject());
            response.setAddress(teacher.getAddress());
            results.add(response);
        }

        return Result.success(results);
    }
}
