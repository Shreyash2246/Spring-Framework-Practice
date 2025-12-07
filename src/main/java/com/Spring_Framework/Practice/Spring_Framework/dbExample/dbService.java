package com.Spring_Framework.Practice.Spring_Framework.dbExample;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class dbService {
    
    // tightly coupled with devDb class
    @Autowired
    private devDb dev;

    // tightly coupled with devDb class
    public String getData(){
        return dev.getData();
    }
}
