package com.aihouse.aihousesys;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
@ComponentScan("com.aihouse.*")
@MapperScan("com.*.*.dao")
@EnableCaching
public class AihouseSysApplication {

	public static void main(String[] args) {
		SpringApplication.run(AihouseSysApplication.class, args);
	}

}
