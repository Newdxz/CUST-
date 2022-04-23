package com.dxz.statement.wxBase;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class WxPush {

    @JsonProperty("appToken")
    private String appToken;
    @JsonProperty("content")
    private String content;
    @JsonProperty("summary")
    private String summary;
    @JsonProperty("contentType")
    private Integer contentType;
    @JsonProperty("topicIds")
    private List<Integer> topicIds;
    @JsonProperty("uids")
    private List<String> uids;
    @JsonProperty("url")
    private String url;
}
