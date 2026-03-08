package com.ims.backend.mapper;

import com.ims.backend.pojo.Teacher;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
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

}
