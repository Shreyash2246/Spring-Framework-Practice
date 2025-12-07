package com.Spring_Framework.Practice.Spring_Framework.dbExample;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class dbService {
    
    @Autowired
    private specificationDB db;  //loosely coupled with specificationDB interface

    // method to get data from db
    public String getData(){
        return db.getData();
    }
}
