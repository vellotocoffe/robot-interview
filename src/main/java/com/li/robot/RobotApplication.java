package com.li.robot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy(proxyTargetClass=true)  
public class RobotApplication {

	public static void main(String[] args) {
		SpringApplication.run(RobotApplication.class, args);
	}

}
