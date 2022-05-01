package com.dxz.statement.api;


import com.dxz.statement.POJO.YiJia.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class YiJiaApi {

    private static final String YIJIA_URL = "https://yun.yun8609.net/";


    // 2022.05.01 易佳修改平台
    private static final String NEW_YIJIA_URL = "https://kmall.chinaums.com/";
    @Resource
    private RestTemplate restTemplate;

    public WxTS getAllYiJiaInfo() throws InterruptedException {
        Map<String, String> params = new HashMap<>();
        //params.put("spid", "49209");
        //params.put("sid", "49480");
        params.put("pageId", "969603320701595648");
        params.put("pageSize", "100");
        String url = NEW_YIJIA_URL + "ecology/web-plat//diy/getGoodsListByPageId?pageId={pageId}&pageSize={pageSize}";
        // YiJiaDTO product = restTemplate.getForObject(url, YiJiaDTO.class, params);
        ResponseEntity<NewYiJiaDTO> responseEntity = restTemplate.getForEntity(url, NewYiJiaDTO.class, params);
        int statusCodeValue = responseEntity.getStatusCodeValue();
        if (statusCodeValue != 200) {
            Thread.sleep(3000L);
        }
        WxTS ts = new WxTS();
        NewYiJiaDTO product = responseEntity.getBody();
        if (product != null) {
            List<NewYiJiaDTO.ProductList> productList = product.getList();
            List<WxTsChild> res = new ArrayList<>();
            for (NewYiJiaDTO.ProductList yiJiaProduct : productList) {
                WxTsChild wxTsChild = new WxTsChild();
                wxTsChild.setMallfreeprice(String.valueOf(yiJiaProduct.getOldPrice()));
                wxTsChild.setSaleqtymonth(yiJiaProduct.getGoodsSale());
                wxTsChild.setName(yiJiaProduct.getGoodsName());
                //wxTsChild.setStockqty(yiJiaProduct.getStockqty());
                //xTsChild.setSize(yiJiaProduct.getSize());
                res.add(wxTsChild);
            }
            ts.setWxTsChildren(res);
            //ts.setResponsetime(product.getResponsetime());
        }

        return ts;
    }
}
