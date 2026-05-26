package com.youtube.transcriber.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;

@Configuration
public class FileConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        File audioFolder = new File("../audio");

        String audioPath = audioFolder.getAbsolutePath();

        System.out.println("Audio Folder Path: " + audioPath);

        registry.addResourceHandler("/audio/**")
                .addResourceLocations("file:///" + audioPath.replace("\\", "/") + "/");
    }
}