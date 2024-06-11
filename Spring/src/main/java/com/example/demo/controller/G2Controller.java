package com.example.demo.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
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
import java.util.Objects;

@RestController
@RequestMapping("/submit")
@CrossOrigin
public class G2Controller {
    @Resource
    StudentMapper studentMapper;
    @Resource
    RuleMapper ruleMapper;

    @PostMapping("/g2")
    public Map<String, String> save2(@RequestBody String info) {

        JSONObject sjsoninfo = JSONObject.parseObject(info);

        String syear = sjsoninfo.getString("年份");

        String sgrade = "研二";
        String sfileurl = sjsoninfo.get("文件地址").toString();

        QueryWrapper<Rule> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("grade",sgrade).eq("year",syear);

        Rule yearrule = ruleMapper.selectOne(queryWrapper); //取出今年的rule
        String rules = yearrule.getRule(); //取出rule的rule字段 (JSON)
        JSONObject rulejson = JSONObject.parseObject(rules); //把rules转成JSON,好取值

        /*
        学位成绩板块
         */
        JSONObject coursejson = (JSONObject) rulejson.get("学位成绩"); //把学位成绩取出

        double courseweight = Double.parseDouble(coursejson.getString("学位成绩权重")); //把学位成绩权重的值取出

        int courseScore = sjsoninfo.getIntValue("学位课成绩总成绩");
        int courseNum = sjsoninfo.getIntValue("学位课数量");
        if (courseNum == 0) {
            // 返回错误响应
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("错误信息", "学位课数量不能为0");
            return errorResponse;
        }

        double coursetotal = courseScore / courseNum * courseweight;

        if (coursetotal > 20) { coursetotal = 20; }

        double stotal = coursetotal;


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
        stotal += sizhengscore;

        /*
        科研能力板块 : 1.学术论文 2.学科竞赛
         */
        JSONObject sciencerule = (JSONObject) (rulejson.get("科研能力"));
        JSONObject sciencescore = (JSONObject) (sjsoninfo.get("科研能力")); //学生的科研能力板块
        /*
                "学术论文":{
            "CCF推荐A类":"30",
            "CCF推荐B类":"25",
            "无":"0"
        },
         */
        JSONObject papervalue = (JSONObject) (sciencerule.get("学术论文")); //规则中的学术论文的分数
        double paper1value = Double.parseDouble(papervalue.get("CCF推荐A类").toString()); //取出A类对应的分值
        double paper2value = Double.parseDouble(papervalue.get("CCF推荐B类").toString());

        JSONArray paperscore = sciencescore.getJSONArray("学术论文"); //学生返回的学术论文

        double paper1 = 0;double paper2 = 0;

        for (Object paper : paperscore){
            JSONObject paperScore = (JSONObject) paper; //学生具体返回的学术论文
            switch (paperScore.getString("类型")){
                case "CCF推荐A类" : paper1+=1;
                case"CCF推荐B类" : paper2+=1;
            }
        }

        double papertotal = paper1* paper1value + paper2*paper2value;

        /*
                "学科竞赛":{
            "国家级一等奖":"30",
            "国家级二等奖":"20",
            "省级一等奖":"15",
            "省级二等奖":"12",
            "市级一等奖":"4",
            "市级二等奖":"3",
            "无":"0"
        }
         */
        JSONObject gamevalue = (JSONObject) (sciencerule.get("学科竞赛"));

        double nation1 = Double.parseDouble(gamevalue.get("国家级一等奖").toString());
        double nation2 = Double.parseDouble(gamevalue.get("国家级二等奖").toString());
        double province1 = Double.parseDouble(gamevalue.get("省级一等奖").toString());
        double province2 = Double.parseDouble(gamevalue.get("省级二等奖").toString());
        double city1 = Double.parseDouble(gamevalue.get("市级一等奖").toString());
        double city2 = Double.parseDouble(gamevalue.get("市级二等奖").toString());

        JSONArray gamescore =  sciencescore.getJSONArray("学科竞赛"); //取出学生返回的学科竞赛[]

        double game1 = 0;
        double game2 = 0;
        double game3 = 0;
        double game4 = 0;
        double game5 = 0;
        double game6 = 0;

        for (Object game : gamescore){
            JSONObject gameScore = (JSONObject) game; //学生具体返回的学科竞赛
            switch (gameScore.getString("类型")){
                case "国家级一等奖":
                    game1 += 1;
                case "国家级二等奖":
                    game2 += 1;
                case "省级一等奖":
                    game3 += 1;
                case "省级二等奖":
                    game4 += 1;
                case "市级一等奖":
                    game5 += 1;
                case "市级二等奖":
                    game6 += 1;
            }
        }

        double gametotal = game1*nation1 + game2*nation2 + game3*province1 + game4*province2 + game5*city1 + game6*city2;

        double sciencetotal = papertotal + gametotal ;
        if(sciencetotal > 50 ){sciencetotal = 50;} //上限50

        stotal += sciencetotal;


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
