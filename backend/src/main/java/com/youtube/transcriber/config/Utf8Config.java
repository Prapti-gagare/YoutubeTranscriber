package com.youtube.transcriber.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.StringHttpMessageConverter;

import java.nio.charset.StandardCharsets;

@Configuration
public class Utf8Config {

    @Bean
    public StringHttpMessageConverter stringHttpMessageConverter() {

        StringHttpMessageConverter converter =
                new StringHttpMessageConverter(
                        StandardCharsets.UTF_8
                );

        converter.setSupportedMediaTypes(
                java.util.List.of(
                        new MediaType(
                                "text",
                                "plain",
                                StandardCharsets.UTF_8
                        )
                )
        );

        return converter;
    }
}