package com.atguigu.spring01.common;

import cn.hutool.core.util.StrUtil;
import com.atguigu.spring01.entity.Account;
import com.atguigu.spring01.exception.CustomException;
import com.atguigu.spring01.service.AdminService;
import com.atguigu.spring01.service.UserService;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * JWT拦截器，用于处理基于JWT的认证和授权
 * 实现HandlerInterceptor接口，在请求处理前进行拦截验证
 */
public class JWTInterceptor implements HandlerInterceptor {
    // 注入管理员服务
    @Resource
    AdminService adminService;
    // 注入用户服务
    @Resource
    UserService userService;
    /**
     * 前置拦截器，在请求处理前进行调用
     * @param request 当前HTTP请求
     * @param response 当前HTTP响应
     * @param handler 请求处理方法
     * @return true:继续流程 false:终端流程
     * @throws Exception 处理异常时抛出
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 从请求头中获取token
        String token = request.getHeader("token");
        // 如果请求头中没有token，则从参数中获取
        if (StrUtil.isEmpty(token))
        {
            token = request.getParameter("token");
        }
        // 如果token为空，抛出无权限异常
        if(StrUtil.isBlank(token))
        {
            throw new CustomException("401","您无权限操作");
        }
    // 声明Account对象，用于存储从数据库查询到的用户信息
        Account account = null;
        try {
             // 声明Account对象，用于存储从数据库查询到的用户信息
            //拿到token的载荷数据
            String audience = JWT.decode(token).getAudience().get(0); // 解析JWT token获取audience字段
            String[] split = audience.split("-"); // 按照分隔符"-"拆分audience字符串
            String userId = split[0]; // 获取用户ID
            String role = split[1]; // 获取用户角色
            if ("ADMIN".equals(role)) // 判断用户角色是否为管理员
            {
                account = adminService.selectById(userId); // 根据用户ID查询管理员信息
            }
            else if ("USER".equals(role)) // 判断用户角色是否为普通用户
            {
                account = userService.selectById(userId); // 根据用户ID查询普通用户信息
            }

        }catch (Exception e){ // 捕获所有可能的异常
            throw new CustomException("401","您无权限操作"); // 抛出无权限异常
        }
        if (account == null){ // 如果查询结果为空，说明用户不存在
            throw new CustomException("401","您无权限操作"); // 抛出无权限异常
        }
        try {
            JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(account.getPassword())).build(); // 创建JWT验证器
            jwtVerifier.verify(token); // 验证JWT token
        }catch (Exception e) {
            throw new CustomException("401","您无权限操作"); // 抛出无权限异常
        }
        return true; // 返回true，继续流程
    }
}
