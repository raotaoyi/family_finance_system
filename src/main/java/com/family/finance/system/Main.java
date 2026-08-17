package com.family.finance.system;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(scanBasePackages = "com.family.finance.system") // 扫描自定义包下所有组件
@MapperScan("com.family.finance.system.mapper") // 扫描Mapper接口包
@EnableTransactionManagement // 开启事务
@EnableScheduling // 开启定时任务（可选，根据业务需要）
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class,args);
    }


    /**
     * 配置MyBatis Plus分页插件（必备，支持分页查询）
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        // 添加分页插件，指定数据库类型为MySQL
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;
    }
}