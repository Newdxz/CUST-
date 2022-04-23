package com.dxz.statement.wxBase;

import com.dxz.statement.POJO.YiJia.WxTS;
import org.springframework.stereotype.Service;

@Service
public interface WxChatService {
    /**
     * 向一个用户推送消息（测试）
     *
     * @param openId  用户ID
     * @param content 内容
     */
    void sendTest(String openId, String content) throws Exception;

    /**
     * Wxpusher推送接口
     *
     * @param content 发送内容
     */
    void sendTest(WxTS content);
}
