package com.atguigu.spring01.exception;

import lombok.Data;

/**
 * 自定义异常类，继承自RuntimeException
 * 用于处理业务逻辑中的异常情况
 */
@Data // 使用Lombok的@Data注解，自动生成getter、setter、toString等方法
public class CustomException extends RuntimeException {
    // 错误代码，用于标识具体的错误类型
    private String code;
    // 错误信息，用于描述具体的错误内容
    private String msg;

    /**
     * 构造方法1：带错误代码和错误信息的构造方法
     * @param code 错误代码
     * @param msg 错误信息
     */
    public CustomException(String code,String msg){
        this.code = code;
        this.msg = msg;
    }
    /**
     * 构造方法2：只带错误信息的构造方法
     * 默认错误代码为"500"
     * @param msg 错误信息
     */
    public CustomException(String msg){
        this.code="500";
        this.msg = msg;
    }

    /**
     * 构造方法3：无参构造方法
     */
    public CustomException(){

    }
}
