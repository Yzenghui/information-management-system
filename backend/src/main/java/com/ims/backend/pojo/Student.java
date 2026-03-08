package com.ims.backend.pojo;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 学生实体类。
 * 对应数据库中的 student 表。
 */
@Data
public class Student {
    private Integer id;           // 主键 ID（自增）
    private String studentId;     // 学号
    private String name;          // 姓名
    private String gender;        // 性别
    private String major;         // 专业
    private String address;      // 籍贯
    private LocalDateTime createTime;   // 创建时间
    private LocalDateTime updateTime;   // 更新时间
}
