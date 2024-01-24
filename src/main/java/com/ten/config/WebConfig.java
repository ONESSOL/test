package com.ten.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private String imagePath = "/upload/image/**";
    private String thumbnailPath = "/upload/thumbnail/**";
    private String boardPath = "/upload/board/**";
    private String imageSavePath = "/file:///C:\\Users\\user\\Desktop\\image\\image\\";
    private String thumbnailSavePath = "/file:///C:\\Users\\user\\Desktop\\image\\thumbnail\\";
    private String boardSavePath = "/file:///C:\\Users\\user\\Desktop\\image\\board\\";

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(imagePath).addResourceLocations(imageSavePath);
        registry.addResourceHandler(thumbnailPath).addResourceLocations(thumbnailSavePath);
        registry.addResourceHandler(boardPath).addResourceLocations(boardSavePath);
    }
}





















