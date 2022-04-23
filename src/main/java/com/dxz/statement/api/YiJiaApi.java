package com.dxz.statement.api;


import com.dxz.statement.POJO.YiJia.WxTS;
import com.dxz.statement.POJO.YiJia.WxTsChild;
import com.dxz.statement.POJO.YiJia.YiJiaDTO;
import com.dxz.statement.POJO.YiJia.YiJiaProduct;
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
    @Resource
    private RestTemplate restTemplate;

    public WxTS getAllYiJiaInfo() throws InterruptedException {
        Map<String, String> params = new HashMap<>();
        params.put("spid", "49209");
        params.put("sid", "49480");
        String url = YIJIA_URL + "/wxmall/wmall/getCateInfoList?sid={sid}&spid={spid}";
        // YiJiaDTO product = restTemplate.getForObject(url, YiJiaDTO.class, params);
        ResponseEntity<YiJiaDTO> responseEntity = restTemplate.getForEntity(url, YiJiaDTO.class, params);
        int statusCodeValue = responseEntity.getStatusCodeValue();
        if (statusCodeValue != 200) {
            Thread.sleep(3000L);
        }
        WxTS ts = new WxTS();
        YiJiaDTO product = responseEntity.getBody();
        if (product != null) {
            List<YiJiaProduct> productlist = product.getData().getProductlist();
            List<WxTsChild> res = new ArrayList<>();
            for (YiJiaProduct yiJiaProduct : productlist) {
                WxTsChild wxTsChild = new WxTsChild();
                wxTsChild.setMallfreeprice(String.valueOf(yiJiaProduct.getMallmprice()));
                wxTsChild.setSaleqtymonth(yiJiaProduct.getSaleqtymonth());
                wxTsChild.setName(yiJiaProduct.getName());
                wxTsChild.setStockqty(yiJiaProduct.getStockqty());
                wxTsChild.setSize(yiJiaProduct.getSize());
                res.add(wxTsChild);
            }
            ts.setWxTsChildren(res);
            ts.setResponsetime(product.getResponsetime());
        }

        return ts;
    }
}
