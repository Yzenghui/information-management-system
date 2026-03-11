package com.ims.backend.mapper;

import com.ims.backend.pojo.Student;
import org.apache.ibatis.annotations.*;

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

    /**
     * 根据姓名模糊查询学生信息。
     *
     * @param name 要查询的姓名（支持模糊匹配，传入部分姓名即可）
     * @return 符合条件的学生列表，如果未找到则返回空列表
     */
    @Select("SELECT id, student_id, name, gender, major, address, create_time, update_time FROM student WHERE name LIKE CONCAT('%', #{name}, '%')")
    List<Student> findByName(@Param("name") String name);
    // CONCAT(...): SQL 函数，用于将多个字符串连接成一个字符串

    /**
     * 插入新学生记录到数据库。
     *
     * @param student 学生对象，包含待插入的学生信息
     * @return 受影响的行数，正常情况下应为 1
     */
    @Insert("INSERT INTO student (student_id, name, gender, major, address, create_time, update_time) VALUES (#{studentId}, #{name}, #{gender}, #{major}, #{address}, #{createTime}, #{updateTime})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int insert(Student student);

    /**
     * 根据学号删除学生记录。
     *
     * @param studentId 学号
     * @return 受影响的行数，正常情况下应为 1
     */
    @Delete("DELETE FROM student WHERE student_id = #{studentId}")
    int deleteByStudentId(@Param("studentId") String studentId);

}
