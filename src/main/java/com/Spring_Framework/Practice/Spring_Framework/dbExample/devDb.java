package com.Spring_Framework.Practice.Spring_Framework.dbExample;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name = "deploy.env", havingValue = "development")
public class devDb implements specificationDB {
    
    public String getData() {
        return "dev DB!";
    }
}
