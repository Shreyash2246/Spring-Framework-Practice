package com.Spring_Framework.Practice.Spring_Framework.dbExample;

import org.springframework.stereotype.Component;

@Component
public class devDb implements specificationDB {
    
    public String getData() {
        return "dev DB!";
    }
}
