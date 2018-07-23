package cn.xlink.parkinglots.client.config;

import cn.xlink.parkinglots.client.filter.RequestMethodFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

	@Bean
	public FilterRegistrationBean filterRegist() {
		FilterRegistrationBean frBean = new FilterRegistrationBean();
		frBean.setFilter(new RequestMethodFilter());
		frBean.addUrlPatterns("/*");
		return frBean;
	}
}
