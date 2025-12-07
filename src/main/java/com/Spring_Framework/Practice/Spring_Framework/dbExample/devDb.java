package com.Spring_Framework.Practice.Spring_Framework.dbExample;

import org.springframework.stereotype.Component;

// tightly coupled with dbService class
@Component
public class devDb {
    
    public String getData() {
        return "dev DB!";
    }
}
