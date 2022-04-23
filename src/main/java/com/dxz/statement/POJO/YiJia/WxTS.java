package com.dxz.statement.POJO.YiJia;

import lombok.Data;

import java.util.List;

@Data
public class WxTS {
    private List<WxTsChild> wxTsChildren;
    private String responsetime;
}
