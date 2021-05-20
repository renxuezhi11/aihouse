package com.aihouse.aihouseweb;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
@ComponentScan("com.aihouse.*")
@MapperScan("com.*.*.dao")
public class AihouseWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(AihouseWebApplication.class, args);
	}

}
