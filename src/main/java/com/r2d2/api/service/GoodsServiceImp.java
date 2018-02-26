package com.r2d2.api.service;

import com.r2d2.api.core.ApiMapping;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Service;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/1/20.
 */
@Service
public class GoodsServiceImp {

    @ApiMapping("addGoods")
    public Goods addGoods(Goods goods,Integer id){
        return  goods;
    }

    @Getter
    @Setter
    @ToString
    public static class Goods implements Serializable{
        private String goodsName;
        private String goodsId;
    }
}
