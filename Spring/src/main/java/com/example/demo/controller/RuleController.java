package com.example.demo.controller;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.common.Result;

import com.example.demo.entity.Rule;

import com.example.demo.mapper.RuleMapper;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/rule")
@CrossOrigin
public class RuleController {
    @Resource
    RuleMapper ruleMapper;



    @PostMapping //用来增加

    public Result<?> save(@RequestBody Rule rule){
        ruleMapper.insert(rule);
        return Result.success();
    }
    @PutMapping //用来更新
    public Result<?> update(@RequestBody Rule rule){
        ruleMapper.updateById(rule);
        return Result.success();
    }
    @DeleteMapping("/{id}") //用来删除，参数代表着从网页接受来的数据,参数是个占位符,取值为@PathVariable
    public Result<?> delete(@PathVariable Long id){
        ruleMapper.deleteById(id);
        return Result.success();
    }
    @GetMapping("/{id}")
    public Result<?> getById(@PathVariable Long id) {
        return Result.success(ruleMapper.selectById(id));
    }
    @GetMapping
    public Result<?> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "10") Integer pageSize,
                              @RequestParam(defaultValue = "") String search) {
        LambdaQueryWrapper<Rule> wrapper = Wrappers.<Rule>lambdaQuery();
        if (StrUtil.isNotBlank(search)) {
            wrapper.like(Rule::getYear, search);
        }
        Page<Rule> RulePage = ruleMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
        return Result.success(RulePage);
    }
}
