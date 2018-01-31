package com.raysmond.blog;

import com.google.common.collect.ImmutableMap;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;


@SpringBootApplication
//@EnableCaching
public class SpringBlogApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(SpringBlogApplication.class);
        app.setDefaultProperties(ImmutableMap.of("spring.profiles.default", Constants.ENV_DEVELOPMENT));
        app.run(args);
    }
}
