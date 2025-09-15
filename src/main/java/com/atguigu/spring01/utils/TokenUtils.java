package com.atguigu.spring01.utils;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.atguigu.spring01.entity.Account;
import com.atguigu.spring01.service.AdminService;
import com.atguigu.spring01.service.UserService;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Date;

/**
 * Token工具类，用于生成JWT令牌
 */
@Component
public class TokenUtils {
    @Resource
    AdminService adminService;  // 注入管理员服务接口
    @Resource
    UserService userService;    // 注入用户服务接口

    static AdminService staticadminService;  // 静态管理员服务接口
    static UserService staticuserService;    // 静态用户服务接口

    /**
     * 初始化方法，在Bean创建完成后执行
     * 将注入的服务实例赋值给静态变量
     */
    @PostConstruct
    public void init(){
        staticadminService = adminService;   // 将管理员服务实例赋值给静态变量
        staticuserService = userService;     // 将用户服务实例赋值给静态变量
    }

    /**
     * 创建JWT令牌
     * @param data 要存储在令牌中的数据，例如用户ID和角色信息
     * @param sign 用于签名的密钥
     * @return 生成的JWT令牌字符串
     */
    public static String createToken(String data,String sign){
        return JWT.create().withAudience(data) //将user_id-role保存到token中作为载荷
                .withExpiresAt(DateUtil.offsetDay(new Date(),1)) //设置过期时间，设置为当前时间的1天后
                .sign(Algorithm.HMAC256(sign)); //使用HMAC256算法进行签名
    }

/**
 * 获取当前登录用户账号信息的方法
 * 通过请求上下文获取当前请求对象，进而获取用户相关信息
 * @return Account 返回当前登录用户的账号对象，如果未登录则可能返回null
 */
    public static Account getCurrentUser(){
        Account account = null;  // 声明Account对象，用于存储从数据库查询到的用户信息
        // 从请求上下文中获取当前请求对象
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String token = request.getHeader("token");  // 从请求头获取token
        if(StrUtil.isBlank(token)){
            token = request.getParameter("token");  // 如果请求头中没有token，则从请求参数中获取
        }
        // 声明Account对象，用于存储从数据库查询到的用户信息
        //拿到token的载荷数据
        String audience = JWT.decode(token).getAudience().get(0); // 解析JWT token获取audience字段
        String[] split = audience.split("-"); // 按照分隔符"-"拆分audience字符串
        String userId = split[0]; // 获取用户ID
        String role = split[1]; // 获取用户角色
        if ("ADMIN".equals(role)) // 判断用户角色是否为管理员
        {
            account = staticadminService.selectById(userId);// 根据用户ID查询管理员信息
            return account;
        }
        else if ("USER".equals(role)) // 判断用户角色是否为普通用户
        {
            account = staticuserService.selectById(userId); // 根据用户ID查询普通用户信息
            return account;
        }
        return account;
    }
}
