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
            log.info("进行数据推送, 不同商品diffProduct = {}", diffProduct);
            wxChatService.sendTest(allYiJiaInfo);
            pre = allYiJiaInfo;
        }

        /*// 2.若两次请求数据的List列表不一致，并且库存均大于0，则为商品上新,进行推送;
        // 2. 2022.04.23 补充逻辑, 尺寸不一致要筛出不同的商品 @dxz;
        if (tsChildren.size() != pre.getWxTsChildren().size() && tsChildren.size() > 0) {
            // 2.1 获取上一次请求中的商品名称
            List<String> oldList = pre.getWxTsChildren().stream().map(WxTsChild::getName).collect(Collectors.toList());
            log.info("oldList = {}", oldList);
            // 2.2 获取本次请求中的商品名称
            List<String> newList = tsChildren.stream().map(WxTsChild::getName).collect(Collectors.toList());
            log.info("newList = {}", newList);
            // 2.3 找出本次请求区别与上次请求的商品名称
            List<String> listDiff = ListUtils.findListDiff(oldList, newList);
            log.info("listDff = {}", listDiff);
            if (listDiff.size() > 0) {
                // 2.4 找出不同商品名称对应的对象
                List<WxTsChild> wxTsChildren = tsChildren.stream()
                        .filter(chile -> listDiff.contains(chile.getName()))
                        .collect(Collectors.toList());
                allYiJiaInfo.setWxTsChildren(wxTsChildren);
                log.info("两次请求数据大小不同,开始推送,新数据为 = {}", listDiff);
                log.info("发送的数据为= {}", allYiJiaInfo.getWxTsChildren());
                wxChatService.sendTest(allYiJiaInfo);
                pre = allYiJiaInfo;
                return;
            }
            *//*if (listDiff.size() > 0) {
                for (String diff : listDiff) {
                    WxTsChild wxTsChild = new WxTsChild();
                    wxTsChild.setName(diff);
                    res.add(wxTsChild);
                }
                allYiJiaInfo.setWxTsChildren(res);
                log.info("两次请求数据大小不同,开始推送,新数据为 = {}", listDiff);
                log.info("发送的数据为= {}", allYiJiaInfo.getWxTsChildren());
                wxChatService.sendTest(allYiJiaInfo);
                pre = allYiJiaInfo;
                return;
            }*//*
        }
        // 3.获取前一次数据信息
        List<WxTsChild> preChildren = pre.getWxTsChildren();
        // 对前一次数据和本次请求按照商品名称进行数据去重
        // 本次请求数据;
        List<String> list = tsChildren.stream().map(WxTsChild::getName).distinct().collect(Collectors.toList());
        // 上一次请求数据
        List<String> list2 = preChildren.stream().map(WxTsChild::getName).distinct().collect(Collectors.toList());
        List<String> diff2List = ListUtils.findListDiff(list2, list);
        if (diff2List.size() > 0) {
            for (String diff : diff2List) {
                WxTsChild wxTsChild = new WxTsChild();
                wxTsChild.setName(diff);
                res.add(wxTsChild);
            }
            allYiJiaInfo.setWxTsChildren(res);
            log.info("数据不同,开始推送! 本次不同为={}", diff2List);
            wxChatService.sendTest(allYiJiaInfo);
            pre = allYiJiaInfo;
        }*/
    }
}
