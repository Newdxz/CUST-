package com.dxz.statement.wxBase;

import com.alibaba.fastjson.JSON;
import com.dxz.statement.POJO.YiJia.WxTS;
import com.dxz.statement.POJO.YiJia.WxTsChild;
import com.dxz.statement.api.YiJiaApi;
import com.dxz.statement.config.MyResultMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class WxChatServiceImpl extends BaseWeChatServiceImpl implements WxChatService {

    @Autowired
    private YiJiaApi yiJiaApi;

    @Override
    public void sendTest(String openId, String content) throws Exception {
        // 模板Id
        String templateId = "2UpjtkOZaCotaWm3kQ67iojrgNIFjrx2YR4TVHWgvKY";
        // 模板参数
        Map<String, WeChatTemplateMsg> sendMag = new HashMap<String, WeChatTemplateMsg>();
        sendMag.put("MSG", new WeChatTemplateMsg(content));
        //sendMag.put("MSG", new WeChatTemplateMsg(content,"#173177"));
        this.send(openId, templateId, sendMag);
    }

    /**
     * 2、发送模版消息
     * openId     用户Id
     * templateId 模板Id   测试: "ngqIpbwh8bUfcSsECmogfXcV14J0tQlEpBO27izEYtY"
     * data       模板参数
     *
     * @param data
     */
    private String send(String openId, String templateId, Map<String, WeChatTemplateMsg> data) throws Exception {
        String accessToken = weChetAccessToken.getToken();
        String url = WxChatConstant.Url.SEND_URL.replace("ACCESS_TOKEN", accessToken);
        //拼接base参数
        Map<String, Object> sendBody = new HashMap<>();
        sendBody.put("touser", openId);               // openId
        sendBody.put("url", "www.baidu.com");         // 点击模板信息跳转地址
        sendBody.put("topcolor", "#FF0000");          // 顶色
        sendBody.put("data", data);                   // 模板参数
        sendBody.put("template_id", templateId);      // 模板Id
        ResponseEntity<String> forEntity = restTemplate.postForEntity(url, sendBody, String.class);
        return forEntity.getBody();
    }

    /**
     * 我的openId:  o8nrg503tfKwepDE4zKeP2g9PujU
     * AT_RUve1Ju7RKZag0f8566jBfmmDx1U93ub
     */
    public void sendTest(WxTS lists) {
        WxPush wxPush = new WxPush();
        String url = "http://wxpusher.zjiecode.com/api/send/message";
        wxPush.setAppToken("AT_RUve1Ju7RKZag0f8566jBfmmDx1U93ub");
        StringBuilder res = new StringBuilder();
        res.append("上新了");

        // 选出库存大于0的;
        List<WxTsChild> wxTsChildren = lists.getWxTsChildren()
                .stream().filter(s -> s.getStockqty() > 0).collect(Collectors.toList());
        if (wxTsChildren.size() > 0) {
            for (WxTsChild wxTsChild : wxTsChildren) {
                res.append(wxTsChild.getName()).append("、");
            }
        }

        //wxPush.setSummary(res.substring(0, res.length() - 1));
        wxPush.setContent(res.substring(0, res.length() - 1));
        List<String> uids = new ArrayList<>();
        //HG
        //uids.add("UID_wjGd0SjnpW4opncY6p7HAk6a1xtp");
        //ZLD
        //uids.add("UID_T0SQBtWH0A4MEFo8ZWbt5So9EQ17");
        //DXZ
        uids.add("UID_Y7V0WkzjKAEArtFzpCO3s7c5DgUU");
        wxPush.setUids(uids);
        wxPush.setContentType(1);
        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        String jsonString = JSON.toJSONString(wxPush);
        HttpEntity<String> formEntity = new HttpEntity<>(jsonString, headers);
        ResponseEntity<String> entity = restTemplate.postForEntity(url, formEntity, String.class);
        log.info("推送接口返回 body= {}",entity.getBody());
        //wxChatService.sendTest(openId, content);
        new MyResultMap().success();
    }
}
