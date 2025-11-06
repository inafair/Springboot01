package com.atguigu.spring01.controller;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.atguigu.spring01.common.Result;
import com.atguigu.spring01.entity.Category;
import com.atguigu.spring01.entity.Introduction;
import com.atguigu.spring01.entity.User;
import com.atguigu.spring01.service.CategoryService;
import com.atguigu.spring01.service.IntroductionService;
import com.atguigu.spring01.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/echarts")
public class EchartsController {
    @Resource
    public CategoryService categoryService;
    @Resource
    public IntroductionService introductionService;
    @Resource
    public UserService userService;
/**
 * 处理饼图数据请求的控制器方法
 * @return 返回包含分类及其对应数量的结果对象
 */
    @GetMapping("/pie")
    public Result pie(){
    // 用于存储饼图数据的列表，每个元素是一个包含分类名称和数量的Map
    List<Map<String, Object>> list = new ArrayList<>();
        //查询寻询所有分类
        List<Category> categories = categoryService.selectAll(new Category());
        List<Introduction> introductions = introductionService.selectAll(new Introduction());
        for (Category category : categories) {
            Long count = introductions.stream().filter( x-> category.getId().equals(x.getCategoryId())).count();
            Map<String , Object> map = new HashMap<>();
            map.put("name",category.getTitle());
            map.put("value",count);
            list.add(map);
        }
        return Result.success(list);
    }

/**
 * 处理获取用户发帖数量统计的请求
 * @return 返回包含用户名列表(x轴)和对应发帖数量(y轴)的统计结果
 */
    @GetMapping("/bar")
    public Result bar(){
    // 创建用于存储用户名和对应发帖数量的映射关系
      Map<String ,Long> map = new HashMap<>();
    // 用于存储图表x轴数据的列表(用户名)
        List<String> xList = new ArrayList<>();
    // 用于存储图表y轴数据的列表(发帖数量)
        List<Long> yList = new ArrayList<>();

        // 查询所有分类
        List<User> users = userService.selectAll(new User());
        //查询所有帖子信息
        List<Introduction> introductions = introductionService.selectAll(new Introduction());

        for(User user : users){
            long count = introductions.stream().filter(x -> user.getId().equals(x.getUserId())).count();
            map.put(user.getName(),count);

        }

        map.entrySet().stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .collect(Collectors.toMap(Map.Entry :: getKey,Map.Entry :: getValue,(e1,e2) -> e1,LinkedHashMap::new));
        for(String key: map.keySet()){
            xList.add(key);
            yList.add(map.get(key));
        }
        if (xList.size() > 5 && yList.size() > 5) {
            xList = xList.subList(0, 5);
            yList = yList.subList(0, 5);
        }
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("xAxis", xList);
        resultMap.put("yAxis", yList);
        return Result.success(resultMap);
        }

        @GetMapping("/line")
    public Result line(){
        Map<String,Object> resultMap = new HashMap<>();
        List<String> xList = new ArrayList<>();
        List<Long> yList = new ArrayList<>();

        //获取最近多少天的年月日
            Date date = new Date();
            DateTime start = DateUtil.offsetDay(date,-7);
            List<String> list = DateUtil.rangeToList(start,date, DateField.DAY_OF_YEAR).stream().map(DateUtil::formatDate).toList();

            //把所有帖子查出来
            List<Introduction> introductions = introductionService.selectAll(new Introduction());
            for(String day :list){
                long count = introductions.stream().filter(x -> ObjectUtil.isNotEmpty(x.getTime()) && x.getTime().contains(day)).count();
                yList.add(count);
            }
            resultMap.put("xAxis",list);
            resultMap.put("yAxis",yList);
        return Result.success(resultMap);
        }
    }
