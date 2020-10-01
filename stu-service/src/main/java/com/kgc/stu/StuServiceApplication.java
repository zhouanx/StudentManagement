package com.kgc.stu;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@EnableDubbo
@SpringBootApplication
public class StuServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(StuServiceApplication.class, args);
    }

}
