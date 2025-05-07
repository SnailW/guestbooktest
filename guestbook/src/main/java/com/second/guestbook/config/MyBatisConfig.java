package com.second.guestbook.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.second.guestbook.mapper")
public class MyBatisConfig {
    // 기본 설정은 application.properties에서 처리
}