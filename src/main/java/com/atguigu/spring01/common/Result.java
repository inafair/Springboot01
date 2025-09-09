package com.atguigu.spring01.common;


import lombok.Data;

/**
 * 统一返回结果类
 * 用于封装接口返回的数据结构，包含状态码、数据和消息信息
 */
@Data
public class Result {
    private String code; //返回编码，用于标识请求处理状态，如"200"表示成功，"500"表示服务器错误等
    Object data; //数据，用于存储接口返回的具体数据内容
    private String msg;//返回错误信息，用于描述请求处理的结果或错误原因

    /**
     * 成功返回结果的方法
     * @param data 接口返回的数据
     * @return 封装好的Result对象，状态码为"200"，消息为"请求成功"
     */
    public static Result success(Object data){
        Result result = new Result();
        result.setCode("200");
        result.setData(data);
        result.setMsg("请求成功");
        return result;
    }

    public static Result success(){
        return success(null);
    }

    /**
     * 错误返回结果的方法（重载）
     * @param msg 错误信息
     * @return 封装好的Result对象，状态码为"500"，消息为传入的错误信息
     */
    public static Result error(String msg){
        Result result = new Result();
        result.setCode("500");
        result.setMsg(msg);
        return result;
    }

    /**
     * 错误返回结果的方法（重载）
     * @param code 自定义状态码
     * @param msg 错误信息
     * @return 封装好的Result对象，状态码和消息为传入的参数值
     */
    public static Result error(String code,String msg){
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }

}
