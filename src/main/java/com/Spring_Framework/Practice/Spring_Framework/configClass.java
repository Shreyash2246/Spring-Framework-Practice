package com.Spring_Framework.Practice.Spring_Framework;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// Marking this class as a configuration class
@Configuration
public class configClass {
    
    // Defining a bean for demo class
    @Bean
    demo getDemo() {
        return new demo();
    }

}
