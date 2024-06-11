package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@TableName("student")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Student {
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    private Integer sno;
    private String sname;
    private String spassword;
    private Double stotal;
    private String isprice;
    private String sinfo;
    private String sgrade;
    private String operator;
    private Date operatetime;
    private Integer deleted;
    private String syear;

}
