package com.lch.hunter;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.lch.hunter.mapper")
public class HunterApplication {

    public static void main(String[] args) {
        SpringApplication.run(HunterApplication.class, args);
    }

}
