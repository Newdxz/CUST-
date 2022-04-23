package com.dxz.statement.wxBase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

public class BaseWeChatServiceImpl {
    // rpc
    @Autowired
    protected RestTemplate restTemplate;


    // 获取token
    @Autowired
    protected WeChetAccessToken weChetAccessToken;

}
