package com.ims.backend.dto.response;

import lombok.Data;

/**
 * 搜索结果响应对象。
 * 统一学生和教师的返回格式，适配前端表格。
 */
@Data
public class SearchResponse {

    /**
     * ID（学号或工号）
     */
    private String id;

    /**
     * 姓名
     */
    private String name;

    /**
     * 性别
     */
    private String gender;

    /**
     * 身份（学生/教师）
     */
    private String profession;

    /**
     * 专业或学科
     */
    private String course;

    /**
     * 籍贯
     */
    private String address;
}
