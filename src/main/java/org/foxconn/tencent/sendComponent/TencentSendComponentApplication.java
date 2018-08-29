package org.foxconn.tencent.sendComponent;

import java.io.IOException;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

@SpringBootApplication
@MapperScan("org.foxconn.tencent.sendComponent.dao")
public class TencentSendComponentApplication{
	public static void main(String[] args) throws IOException {
		SpringApplication.run(TencentSendComponentApplication.class, args);
	}
}
	