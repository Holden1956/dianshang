package com.example.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringBootConfiguration;

/**
 * @author:xuanhe
 * @date:2024/2/27 11:15
 * @modyified By:
 */
@SpringBootConfiguration// 配置类
@MapperScan("com.example.mapper")// 扫描指定包，自动生成代理类
public class MybatisConfig {

}
