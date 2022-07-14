package com.cxc.fileservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;


/**
 * @author chenxiangcai
 */
@Configuration
@EnableWebMvc
public class StaticResourceConfig implements WebMvcConfigurer {

    @Value("${fileServer.resource-location}")
    private String resourceLocation;

    @Value("${fileServer.path-pattern}")
    private String pathPattern;
    @Resource
    private FileServerInterceptor fileServerInterceptor;
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(pathPattern).addResourceLocations(resourceLocation);
    }

    @Override
    public void addInterceptors(org.springframework.web.servlet.config.annotation.InterceptorRegistry registry) {
        registry.addInterceptor(fileServerInterceptor).excludePathPatterns("/error");
    }
}

