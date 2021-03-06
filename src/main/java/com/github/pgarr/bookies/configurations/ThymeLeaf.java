package com.github.pgarr.bookies.configurations;

import nz.net.ultraq.thymeleaf.LayoutDialect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ThymeLeaf {
    @Bean
    public LayoutDialect layoutDialect() {
        return new LayoutDialect();
    }
}