package com.dxz.statement.POJO.YiJia;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @title: NewYiJiaDTO
 * @Author dxz
 * @Date: 2022/5/1 0001 16:27
 */
@Data
public class NewYiJiaDTO {
    private String message;
    private String code;
    private List<ProductList> list;

    @NoArgsConstructor
    @Data
    public static class ProductList {

        @JsonProperty("goodsSale")
        private Integer goodsSale;
        @JsonProperty("goodsMinMaxPrice")
        private String goodsMinMaxPrice;
        @JsonProperty("oldPrice")
        private String oldPrice;
        @JsonProperty("openVip")
        private String openVip;
        @JsonProperty("goodsImages")
        private String goodsImages;
        @JsonProperty("hasDisPrice")
        private Boolean hasDisPrice;
        @JsonProperty("joinMbrDiscount")
        private String joinMbrDiscount;
        @JsonProperty("goodsRetailPrice")
        private String goodsRetailPrice;
        @JsonProperty("checkStatus")
        private String checkStatus;
        @JsonProperty("goodsRest")
        private Integer goodsRest;
        @JsonProperty("goodsSaleSpecial")
        private Integer goodsSaleSpecial;
        @JsonProperty("id")
        private String id;
        @JsonProperty("goodsName")
        private String goodsName;
        @JsonProperty("goodsMinPrice")
        private String goodsMinPrice;
        @JsonProperty("status")
        private String status;
    }
}
