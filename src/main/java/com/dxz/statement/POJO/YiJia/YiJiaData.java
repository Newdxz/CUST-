package com.dxz.statement.POJO.YiJia;

import lombok.Data;

import java.util.List;

@Data
public class YiJiaData {
    private Integer count;

    private String fileurl;

    private List<YiJiaProduct> productlist;
}
