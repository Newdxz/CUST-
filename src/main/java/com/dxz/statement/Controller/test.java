package com.dxz.statement.Controller;

import com.dxz.statement.POJO.YiJia.WxTS;
import com.dxz.statement.POJO.testA;
import com.dxz.statement.api.YiJiaApi;
import com.dxz.statement.server.testAImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/Book")
public class test {

    @Autowired
    private testAImpl testAImpl;

    @Autowired
    private YiJiaApi yjApi;


    @RequestMapping("/test")
    public List<testA> restTemplateGetTest() {
        return testAImpl.getAll();
    }

    @RequestMapping("/test1")
    public WxTS getAll() throws InterruptedException {
        return yjApi.getAllYiJiaInfo();
    }
}
