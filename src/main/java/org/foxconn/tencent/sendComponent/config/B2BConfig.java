package org.foxconn.tencent.sendComponent.config;

import org.foxconn.tencent.sendComponent.entity.Config.B2BUrlEntity;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class B2BConfig {
	@Bean
	@ConfigurationProperties(prefix="b2b")
	public B2BUrlEntity b2BUrlEntity(){
		return new B2BUrlEntity();
	}
}
