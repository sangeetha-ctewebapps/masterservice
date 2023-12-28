package com.lic.epgs.mst.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.lic.epgs.rhssofilter.RequestResponseTokenCheck;

@Configuration
public class FilterRegisterBean {
	@Bean
	public FilterRegistrationBean<RequestResponseTokenCheck> loggingFilter() {
		FilterRegistrationBean<RequestResponseTokenCheck> registrationBean = new FilterRegistrationBean<>();

		registrationBean.setFilter(new RequestResponseTokenCheck());
		registrationBean.addUrlPatterns("/sso/auth/*");
		// registrationBean.setOrder(2);

		return registrationBean;
	}
}
