package com.example.demo.controller;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.common.Result;
import com.example.demo.entity.Student;
import com.example.demo.mapper.StudentMapper;

import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/student")
@CrossOrigin
public class StudentController { @Resource

StudentMapper studentMapper;

    @PostMapping("/login") //用来登录

    public Result<?> login(@RequestBody Student student){
        Student res = studentMapper.selectOne(Wrappers.<Student>lambdaQuery().eq(Student::getSno, student.getSno()).eq(Student::getSpassword, student.getSpassword()));
        if(res == null){
            return Result.error("-1","学号或密码错误");
        }
        return Result.success(res); //会返回整个res 这样前端才可以把登录的用户名密码放入sessionstorage ！！！！！

    }

    @PostMapping //用来增加
    public Result<?> save(@RequestBody Student student){
        if(student.getSpassword()==null){
            student.setSpassword("123456");
        }
        studentMapper.insert(student);
        return Result.success();

    }
    @PutMapping //用来更新
    public Result<?> update(@RequestBody Student student){
        studentMapper.updateById(student);
        return Result.success();

    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id) {
        Student student = studentMapper.selectById(id);
        if (student == null) {
            return Result.error("1","没有这个学生");
        } else {
            // 执行逻辑删除
            student.setDeleted(1); // 或者使用 true 表示逻辑删除
            studentMapper.updateById(student);
            return Result.success();
        }
    }


    @GetMapping("/{id}")
    public Result<?> getById(@PathVariable Long id) {
        return Result.success(studentMapper.selectById(id));
    }


    @GetMapping //用来查询
    public Result<?> findPage(@RequestParam(defaultValue = "1") Integer pageNum,@RequestParam(defaultValue = "10") Integer pageSize,@RequestParam(defaultValue = "") String search){
        LambdaQueryWrapper<Student> wrapper = Wrappers.<Student>lambdaQuery();
        if (StrUtil.isNotBlank(search)){ //hutool的工具类
            wrapper.like(Student::getSname, search);
        }
        Page<Student> studentPage = studentMapper.selectPage(new Page<>(pageNum,pageSize),wrapper);

        return Result.success(studentPage);
    }
}
