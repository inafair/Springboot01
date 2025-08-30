package com.atguigu.spring01.exception;

import com.atguigu.spring01.common.Result;
import org.slf4j.Logger;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 全局异常处理器
 * 使用@ControllerAdvice注解标记此类为全局异常处理类
 * 指定basePackages为"com.atguigu.spring01.controller"，表示只处理该包下Controller的异常
 */
@ControllerAdvice("com.atguigu.spring01.controller")
public class GlobalExceptionHandle {

    // 创建日志记录器，用于记录系统异常信息
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(GlobalExceptionHandle.class);
    /**
     * 异常处理方法
     * @ExceptionHandler注解标记此方法为异常处理方法
     * Exception.class表示此方法处理所有类型的异常
     * @ResponseBody注解表示将返回的Result对象转换成JSON格式返回给前端
     * @param e 捕获到的异常对象
     * @return 返回一个Result对象，包含错误信息
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody //将result对象转换成json形式
    public Result error(Exception e){
        // 记录系统异常日志，包含异常堆栈信息
        log.error("系统异常",e);
        // 返回错误信息给前端
        return Result.error("系统异常");
    }
    @ExceptionHandler(CustomException.class)
    @ResponseBody
    public Result error(CustomException e){
        // 记录系统异常日志，包含异常堆栈信息
        log.error("自定义错误",e);
        // 返回错误信息给前端
        return Result.error(e.getCode(),e.getMsg());}

}
