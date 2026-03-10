package com.ims.backend.mapper;

import com.ims.backend.pojo.Teacher;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 教师数据访问接口。
 * 这是一个接口，用于定义与教师数据访问相关的方法。
 */
@Mapper
public interface TeacherMapper {

    /**
     * 查询所有教师信息。
     *
     * @return 教师对象列表，每个教师对象包含数据库中所有字段的值
     */
    @Select("SELECT id, teacher_id, name, gender, subject, address, create_time, update_time FROM teacher")
    List<Teacher> findAll();

    /**
     * 根据姓名模糊查询教师信息。
     *
     * @param name 要查询的姓名（支持模糊匹配，传入部分姓名即可）
     * @return 符合条件的教师列表，如果未找到则返回空列表
     */
    @Select("SELECT id, teacher_id, name, gender, subject, address, create_time, update_time FROM teacher WHERE name LIKE CONCAT('%', #{name}, '%')")
    List<Teacher> findByName(@Param("name") String name);

    /**
     * 插入新教师记录到数据库。
     *
     * @param teacher 教师对象，包含待插入的教师信息
     * @return 受影响的行数，正常情况下应为 1
     */
    @Insert("INSERT INTO teacher (teacher_id, name, gender, subject, address, create_time, update_time) VALUES (#{teacherId}, #{name}, #{gender}, #{subject}, #{address}, #{createTime}, #{updateTime})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int insert(Teacher teacher);

}
