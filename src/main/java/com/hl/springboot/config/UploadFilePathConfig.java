package com.hl.springboot.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 配置文件虚拟路径
 */
@Configuration
public class UploadFilePathConfig implements WebMvcConfigurer {

    @Value("${files.upload.staticAccessPath}")
    private String staticAccessPath;

    @Value("${files.upload.path}")
    private String uploadRealPath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(staticAccessPath).addResourceLocations("file:" + uploadRealPath);
    }

}
