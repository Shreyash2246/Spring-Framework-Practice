package com.Spring_Framework.Practice.Spring_Framework;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;


@Configuration
public class configClass {

    @Bean
    @Scope("prototype") // Setting the scope to prototype
    demo getDemo() {
        return new demo();
    }

}
