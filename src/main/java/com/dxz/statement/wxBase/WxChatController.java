package com.dxz.statement.wxBase;

import com.dxz.statement.POJO.YiJia.WxTS;
import com.dxz.statement.POJO.YiJia.WxTsChild;
import com.dxz.statement.api.YiJiaApi;
import com.dxz.statement.utils.ListUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 微信推送消息主类
 */
@Component
@Slf4j
public class WxChatController {

    @Autowired
    private WxChatService wxChatService;


    private static WxTS pre = null;

    @Autowired
    private YiJiaApi yjApi;


    @Scheduled(cron = "0/10 * * * * ?")
    public void diff() throws InterruptedException {
        List<WxTsChild> res = new ArrayList<>();
        // 1. 获取易佳超市信息;
        WxTS allYiJiaInfo = yjApi.getAllYiJiaInfo();
        // 过滤出库存大于0的商品
        List<WxTsChild> tsChildren = allYiJiaInfo.getWxTsChildren().stream()
                .filter(t -> t.getStockqty() > 0).collect(Collectors.toList());
        log.info("本次请求商品为 = {}", tsChildren.stream().map(WxTsChild::getName).collect(Collectors.toList()));
        allYiJiaInfo.setWxTsChildren(tsChildren);
        // 前一轮数据为空,则另pre = 首次获取到的数据,避免应用首次启动和重启导致的信息重发;
        if (pre == null) {
            log.info("pre 数据为空,进行赋值操作");
            pre = allYiJiaInfo;
        }
        // 2. NEW-> 2022.04.24 新增逻辑,合并两次情况,统一均发送数据;
        List<String> oldList = pre.getWxTsChildren().stream()
                .map(WxTsChild::getName)
                .distinct()
                .collect(Collectors.toList());

        List<String> newList = tsChildren.stream()
                .map(WxTsChild::getName)
                .distinct()
                .collect(Collectors.toList());
        List<String> diffProduct = ListUtils.findListDiff(oldList, newList);
        if (diffProduct.size() > 0) {
            List<WxTsChild> wxTsChildren = tsChildren.stream()
                    .filter(TsChild -> diffProduct.contains(TsChild.getName()))
                    .collect(Collectors.toList());
            allYiJiaInfo.setWxTsChildren(wxTsChildren);
            log.info("上次请求商品为 = {}", oldList);
            log.info("进行数据推送, 不同商品diffProduct = {}", diffProduct);
            wxChatService.sendTest(allYiJiaInfo);
            pre = allYiJiaInfo;
        }
    }
}
