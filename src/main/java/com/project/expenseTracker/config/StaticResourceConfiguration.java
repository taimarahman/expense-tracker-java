//package com.project.expenseTracker.config;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//import org.springframework.web.servlet.resource.PathResourceResolver;
//
//@Configuration
//public class StaticResourceConfiguration implements WebMvcConfigurer {
//
//    @Value("${profile.images.directory}")
//    private String profileImagesDirectory;
//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry){
//        registry.addResourceHandler("/profile-images/**")
//                .addResourceLocations(profileImagesDirectory)
//                .setCachePeriod(3600)
//                .resourceChain(true)
//                .addResolver(new PathResourceResolver());
//    }
//}
