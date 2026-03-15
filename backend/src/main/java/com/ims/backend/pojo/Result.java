package com.ims.backend.pojo;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 统一API响应封装类。
 * 所有Controller的返回值都应使用此类进行包装，以保证前后端数据交互格式的统一。
 *
 * @param <T> 响应中数据载荷(`data`字段)的类型
 */
@Data // Lombok注解，自动生成getter、setter等方法
public class Result<T> {

    /**
     * 业务状态码。
     * 遵循HTTP状态码语义，但可扩展自定义业务码。
     * 例如：200-成功，400-客户端请求错误，401-未授权，500-服务器内部错误。
     */
    private Integer code;

    /**
     * 提示信息。用于给前端或调用者展示简要的操作结果说明。
     */
    private String message;

    /**
     * 响应数据载荷。泛型<T>使得可以返回任何类型的数据。
     * 成功时，此字段包含业务数据；失败时，此字段通常为null。
     */
    private T data;

    /**
     * 响应时间戳。记录服务器生成此响应的时间，用于调试和监控。
     */
    private LocalDateTime timestamp;

    // ========== 以下是快速创建成功/失败响应的静态工具方法 ==========

    /**
     * 快速创建一个成功响应。
     *
     * @param data 要返回的业务数据
     * @param <T>  业务数据的类型
     * @return 包装了成功状态和数据的Result对象
     */
    public static <T> Result<T> success(T data) {
        Result<T> result = new Result<>();
        result.setCode(200);
        result.setMessage("操作成功");
        result.setData(data);
        result.setTimestamp(LocalDateTime.now());
        return result;
    }

    /**
     * 快速创建一个成功响应（无数据）。
     * 适用于仅需告知操作成功，无需返回数据的场景（如删除、更新操作）。
     *
     * @return 包装了成功状态、data字段为null的Result对象
     */
    public static Result<?> success() {
        return success(null);
    }

    /**
     * 快速创建一个错误响应。
     *
     * @param code    错误状态码
     * @param message 错误提示信息
     * @return 包装了错误状态的Result对象（data字段为null）
     */
    public static Result<?> error(Integer code, String message) {
        Result<?> result = new Result<>();
        result.setCode(code);
        result.setMessage(message);
        result.setTimestamp(LocalDateTime.now());
        // 错误响应不设置data字段，或可设置为更详细的错误对象
        return result;
    }
}