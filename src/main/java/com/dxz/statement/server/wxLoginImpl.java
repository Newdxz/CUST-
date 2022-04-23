package com.dxz.statement.server;

import com.dxz.statement.POJO.wxLogin;
import com.dxz.statement.mapper.wxLoginMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class wxLoginImpl {


    @Autowired
    wxLoginMapper wxLoginMapper;


    public int insertUser(wxLogin wxLogin) {
        return wxLoginMapper.insertLoginrecord(wxLogin);
    }

}
