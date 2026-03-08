package com.ims.backend.pojo;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 教师实体类。
 * 对应数据库中的 teacher 表。
 */
@Data
public class Teacher {
    private Integer id;           // 主键 ID（自增）
    private String teacherId;     // 工号
    private String name;          // 姓名
    private String gender;        // 性别
    private String subject;         // 所授学科
    private String address;      // 籍贯
    private LocalDateTime createTime;   // 创建时间
    private LocalDateTime updateTime;   // 更新时间
}
