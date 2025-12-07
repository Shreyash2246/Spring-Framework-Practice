package com.Spring_Framework.Practice.Spring_Framework.dbExample;

import org.springframework.stereotype.Service;


@Service
public class dbService {
    
    final specificationDB db;

    public dbService(specificationDB db){
        this.db = db;
    }

    public String getData(){
        return db.getData();
    }
}
