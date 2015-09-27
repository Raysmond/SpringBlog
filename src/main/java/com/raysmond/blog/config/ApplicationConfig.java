package com.raysmond.blog.config;

import static org.springframework.context.annotation.ComponentScan.Filter;

import com.raysmond.blog.BlogApp;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;


@Configuration
@ComponentScan(basePackageClasses = BlogApp.class, excludeFilters = @Filter({Controller.class, Configuration.class}))
class ApplicationConfig {
	@Bean
	public static PropertyPlaceholderConfigurer propertyPlaceholderConfigurer() {
		PropertyPlaceholderConfigurer ppc = new PropertyPlaceholderConfigurer();

		ppc.setLocations(
				new ClassPathResource("/persistence.properties"),
				new ClassPathResource("/redis.properties"));

		return ppc;
	}

}
