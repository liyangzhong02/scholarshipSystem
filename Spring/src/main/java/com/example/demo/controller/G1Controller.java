package com.example.demo.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.common.Result;
import com.example.demo.entity.Rule;
import com.example.demo.entity.Student;
import com.example.demo.mapper.RuleMapper;
import com.example.demo.mapper.StudentMapper;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/submit")
@CrossOrigin
public class G1Controller {
    @Resource
    StudentMapper studentMapper;
    @Resource
    RuleMapper ruleMapper;
    @PostMapping("/g1")
    public Map<String,String> save1(@RequestBody String info){

        JSONObject sjsoninfo = JSONObject.parseObject(info);

        String syear = sjsoninfo.getString("年份");

        String sgrade = "研一";

        QueryWrapper<Rule> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("grade",sgrade).eq("year",syear);

        Rule yearrule = ruleMapper.selectOne(queryWrapper); //取出今年的rule，此时为string
        String rules = yearrule.getRule(); //取今年的rule的研一的具体rule
        JSONObject rulejson = JSONObject.parseObject(rules);//把今年的rule的研一的rule转成json
        JSONObject coursejson = (JSONObject) rulejson.get("学位成绩"); //把学位成绩取出来
        /*
        "学位成绩": {
      "学位成绩权重": "0.2"
    }
         */
        double courseweight = Double.parseDouble(coursejson.getString("学位成绩权重")); //把学位成绩权重的值取出

        int courseScore = sjsoninfo.getIntValue("学位课成绩总成绩");
        int courseNum = sjsoninfo.getIntValue("学位课数量");
        double stotal = 0.0; // 设置一个默认值

        if (courseNum != 0) {
            stotal = courseScore / (double) courseNum * courseweight;
        }

        String sno = sjsoninfo.getString("学号");

        Student student = studentMapper.selectOne(new QueryWrapper<Student>().eq("sno", sno));
        if (student != null) {
            student.setStotal(stotal);
            studentMapper.updateById(student);
        } //把stotal插入对应的学生

        Map<String, String> resultMap = new HashMap<>();
        resultMap.put("申请状态", "success");

        return resultMap;

    }


}
