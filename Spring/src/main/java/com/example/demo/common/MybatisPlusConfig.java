package com.example.demo.common;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
//import com.baomidou.mybatisplus.extension.plugins.paginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @date 2023/6/22
 * @description mybatisplus分页
 *
 */
@Configuration
@MapperScan("com.example.demo.mapper")
public class MybatisPlusConfig {
    @Bean
    public PaginationInnerInterceptor PaginationInnerInterceptor()
    {
        return new PaginationInnerInterceptor();
    }
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor(){
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor());
        return interceptor;
    }
}
