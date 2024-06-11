package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@TableName("rule")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Rule {
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    private String rule; //实际上是json
    private Integer year;
    private String grade;

}
