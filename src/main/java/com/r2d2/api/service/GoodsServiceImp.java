package com.r2d2.api.service;

import com.r2d2.api.core.ApiMapping;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/1/20.
 */
public class GoodsServiceImp {

    @ApiMapping(" ")
    public Goods addGoods(Goods goods,Integer id){
        return  goods;
    }

    @Getter
    @Setter
    @ToString
    public static class Goods implements Serializable{
        private String goodsName;
        private String goodsId;

        public String getGoodsName() {
            return goodsName;
        }

        public void setGoodsName(String goodsName) {
            this.goodsName = goodsName;
        }

        public String getGoodsId() {
            return goodsId;
        }

        public void setGoodsId(String goodsId) {
            this.goodsId = goodsId;
        }

    }
}
