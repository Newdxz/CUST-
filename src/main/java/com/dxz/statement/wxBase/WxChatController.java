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
        log.info(" = {}",tsChildren);
        allYiJiaInfo.setWxTsChildren(tsChildren);
        // 前一轮数据为空,则另pre = 首次获取到的数据,避免应用首次启动和重启导致的信息重发;
        if (pre ==  null) {
            pre = allYiJiaInfo;
        }
        //log.info("pre = {}, now = {}", pre.getWxTsChildren().stream().map(e -> e.getName()).collect(Collectors.toList()),
        //       allYiJiaInfo.getWxTsChildren().stream().map(e -> e.getName()).collect(Collectors.toList()));
        // 2.若两次请求数据的List列表不一致，并且库存均大于0，则为商品上新,进行推送;
        // 2. 2022.04.23 补充逻辑, 尺寸不一致要筛出不同的商品 @dxz;
        if (tsChildren.size() != pre.getWxTsChildren().size() && tsChildren.size() > 0) {
            List<String> oldList = pre.getWxTsChildren().stream().map(e -> e.getName()).collect(Collectors.toList());
            List<String> newList = tsChildren.stream().map(e -> e.getName()).collect(Collectors.toList());
            List<String> listDiff = ListUtils.findListDiff(oldList, newList);
            if (listDiff.size() > 0) {
                for (String diff : listDiff) {
                    WxTsChild wxTsChild = new WxTsChild();
                    wxTsChild.setName(diff);
                    res.add(wxTsChild);
                }
                allYiJiaInfo.setWxTsChildren(res);
                log.info("两次请求数据大小不同,开始推送,新数据为 = {}", listDiff);
                wxChatService.sendTest(allYiJiaInfo);
                pre = allYiJiaInfo;
                return;
            }
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
        }
    }
}
