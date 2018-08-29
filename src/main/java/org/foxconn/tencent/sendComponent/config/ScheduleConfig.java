package org.foxconn.tencent.sendComponent.config;

import javax.annotation.Resource;

import org.foxconn.tencent.sendComponent.schedule.ScheduleManger;
import org.foxconn.tencent.sendComponent.schedule.ScheduleRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ScheduleConfig {
	@Resource
	ScheduleRunner runer;
	
	@Bean
	public ScheduleManger scheduleManger(){
		runer.run();
		ScheduleManger manager = new ScheduleManger();
		return manager;
	}
	
}
