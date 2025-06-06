package com.example.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.pojo.Result;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 处理权限异常
    @ExceptionHandler(ForbiddenException.class)
    public Result handleForbidden(ForbiddenException e) {
        return Result.error("需要管理员权限");
    }

    // 处理所有异常
    @ExceptionHandler(Exception.class)
    public Result handleException(Exception e) {
        e.printStackTrace();
        return Result.error("服务器异常，请稍后再试！");
    }
}
