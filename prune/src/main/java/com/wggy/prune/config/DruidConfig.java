package com.wggy.prune.config;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * @author ping
 * @create 2019-03-25 18:06
 **/
@Configuration
public class DruidConfig {
    @Bean
    @ConfigurationProperties("spring.datasource.druid")
    public DataSource druidDataSource(){
        return DruidDataSourceBuilder.create().build();
    }
}
