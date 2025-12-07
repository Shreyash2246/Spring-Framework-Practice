package com.Spring_Framework.Practice.Spring_Framework.dbExample;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
public class prodDb implements specificationDB {
    
    public String getData() {
        return "prod DB!";
    }
}
