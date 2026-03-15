package com.ims.backend.config;

import com.ims.backend.pojo.Result;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器。
 * 集中处理所有 Controller 抛出的异常，返回统一格式的响应。
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理权限不足异常（403）。
     * 当用户没有访问某个接口的权限时抛出。
     */
    @ExceptionHandler(AccessDeniedException.class)
    public Result<?> handleAccessDeniedException(AccessDeniedException e) {
        return Result.error(403, "权限不足：您没有访问该资源的权限");
    }

    /**
     * 处理业务异常（RuntimeException）。
     * Service 层或 Controller 层主动抛出的业务错误。
     */
    @ExceptionHandler(RuntimeException.class)
    public Result<?> handleRuntimeException(RuntimeException e) {
        return Result.error(500, e.getMessage());
    }

    /**
     * 处理其他所有未知异常。
     * 作为兜底方案，避免向前端暴露敏感信息。
     */
    @ExceptionHandler(Exception.class)
    public Result<?> handleException(Exception e) {
        // 生产环境中应该记录日志，而不是直接返回异常信息
        return Result.error(500, "系统繁忙，请稍后再试");
    }
}
