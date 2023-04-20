package com.group29.config;

import com.group29.interceptor.JWTInterceptor;
import lombok.ToString;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author ryyyyyy
 * @create 2023-04-19 7:16
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new JWTInterceptor())
                .addPathPatterns("/**").excludePathPatterns("/user/**").excludePathPatterns("/user").excludePathPatterns("/templates/error/**")
                .excludePathPatterns("/error").excludePathPatterns("/").excludePathPatterns("/favicon.ico");
    }
}
