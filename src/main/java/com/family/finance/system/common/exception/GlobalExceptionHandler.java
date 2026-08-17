package com.family.finance.system.common.exception;

import com.family.finance.system.common.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(RuntimeException.class)
    public Result<?> runtimeException(RuntimeException e) {
        return Result.fail(500, e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public Result<?> exception(Exception e) {
        log.error("服务器异常", e);
        return Result.fail(500, "服务器异常");
    }
}