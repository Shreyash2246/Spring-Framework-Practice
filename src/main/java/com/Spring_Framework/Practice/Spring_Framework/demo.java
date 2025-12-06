package com.Spring_Framework.Practice.Spring_Framework;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

public class demo {
    
    void display() {
        System.out.println("Hello world!");
    }

    // Method to be called before the bean is created
    @PostConstruct
    void callmebeforedemoiscreated() {
        System.out.println("This is called before creating this bean demo");
    }

    @PreDestroy
    void callmeafterdemoisdestroyed() {
        System.out.println("This is called before destroying this bean demo");
    }
}
