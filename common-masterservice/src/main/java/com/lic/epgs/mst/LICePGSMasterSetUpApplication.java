package com.lic.epgs.mst;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@EnableCaching
@SpringBootApplication
public class LICePGSMasterSetUpApplication extends SpringBootServletInitializer {

	// private static final Logger LOGGER =
	// LogManager.getLogger(LICePGSMasterSetUpApplication.class);

	public static void main(String[] args) {
		ApplicationContext ctx = SpringApplication.run(LICePGSMasterSetUpApplication.class, args); 

	}

	@Bean
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}

}
