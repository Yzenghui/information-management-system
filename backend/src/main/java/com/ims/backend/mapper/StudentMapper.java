package com.ims.backend.mapper;

import com.ims.backend.pojo.Student;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import java.util.List;

/**
 * 学生数据访问接口（Mapper）。
 * 负责定义与 student 表交互的数据库操作。
 */
@Mapper
public interface StudentMapper {

    /**
     * 查询所有学生信息。
     *
     * @return 学生对象列表，每个学生对象包含数据库中所有字段的值
     */
    @Select("SELECT id, student_id, name, gender, major, address, create_time, update_time FROM student")
    List<Student> findAll();
    // MyBatis 会自动将数据库的下划线命名（student_id）映射为 Java 的驼峰命名（studentId）
}
