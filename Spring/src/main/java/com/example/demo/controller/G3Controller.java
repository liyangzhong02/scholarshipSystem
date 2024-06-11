package com.example.demo.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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
public class G3Controller {
    @Resource
    StudentMapper studentMapper;
    @Resource
    RuleMapper ruleMapper;

    @PostMapping("/g3")
    public Map<String, String> save3(@RequestBody String info){

        JSONObject sjsoninfo = JSONObject.parseObject(info);
        String syear = sjsoninfo.getString("年份");

        String sgrade = "研三";

        QueryWrapper<Rule> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("grade",sgrade).eq("year",syear);

        Rule yearrule = ruleMapper.selectOne(queryWrapper); //取出今年的哪一行rule
        String rules = yearrule.getRule(); //取出那一行rule的rule字段 （json）
        JSONObject rulejson = JSONObject.parseObject(rules); //把rules转成json 好取值
        System.out.println(rulejson);
        /*
        思政表现板块
         */
        JSONObject policyrule = (JSONObject) (rulejson.get("思政表现")); //取出思政表现{}
        JSONObject policyscore = (JSONObject) (sjsoninfo.get("思政表现")); //取出学生info里的思政表现{}
        //荣誉加分
        JSONObject honorvalue = (JSONObject) (policyrule.get("荣誉加分")); //取出rule表对应应该加多少分
        int honorscore = Integer.parseInt(honorvalue.get(policyscore.get("荣誉加分")).toString()); //取出学生应该得到的分值
        //导师组评价
        JSONObject teachervalue = (JSONObject)  (policyrule.get("导师组评价"));
        int teacherscore = Integer.parseInt(teachervalue.get(policyscore.get("导师组评价")).toString());
        //基础分
        int pervalue = Integer.parseInt(policyrule.get("通报每次所扣分数").toString());
        int Notificationtimes = Integer.parseInt(policyscore.get("通报次数").toString());
        int Notificationscore = pervalue * Notificationtimes;
        
        int basicscore = 20;
        basicscore -= Notificationscore; //通报扣分
        if(basicscore < 0){ basicscore = 0; }; //不能小于0
        int sizhengscore = basicscore + honorscore +teacherscore;
        if(sizhengscore > 30) { sizhengscore = 30; }
        double stotal = sizhengscore;

        /*
        科研能力板块
         */
        JSONObject sciencerule = (JSONObject) (rulejson.get("科研能力"));
        JSONObject sciencescore = (JSONObject) (sjsoninfo.get("科研能力"));
        //学术论文
        JSONObject papervalue = (JSONObject) (sciencerule.get("学术论文"));
        int paperscore = Integer.parseInt(papervalue.get(sciencescore.get("学术论文")).toString());
        //知识产权
        JSONObject knowledgevalue = (JSONObject) (sciencerule.get("知识产权"));
        int knowledgescore = Integer.parseInt(knowledgevalue.get(sciencescore.get("知识产权")).toString());

        int sciencetotal = paperscore + knowledgescore ;
        if(sciencetotal > 50 ){sciencetotal = 50;} //上限50

        stotal += sciencetotal;

        String sname = sjsoninfo.getString("姓名");
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
