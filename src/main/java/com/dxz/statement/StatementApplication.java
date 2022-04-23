package com.dxz.statement;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan("com.dxz.statement.mapper")
@EnableScheduling
public class StatementApplication {


    public static void main(String[] args) {
        SpringApplication.run(StatementApplication.class, args);
    }


}
