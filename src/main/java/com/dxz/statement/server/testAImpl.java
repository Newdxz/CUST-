package com.dxz.statement.server;

import com.dxz.statement.POJO.testA;
import com.dxz.statement.mapper.testAmapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class testAImpl {

    @Autowired
    testAmapper testAmapper;


    public List<testA> getAll() {
        return testAmapper.selectAll();
    }


}
