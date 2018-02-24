package com.r2d2.api.service;

import com.r2d2.api.core.ApiMapping;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * Created by Administrator on 2018/1/20.
 */
@Service
public class UserServiceImpl {

    @ApiMapping(value = "getUserInfo",checkLogin = true)  //TODO
    public UserInfo getUserInfo(Long userId){
        Assert.notNull(userId);
        UserInfo info = new UserInfo();
        info.setName("小明");
        info.setSex("man");
        info.setUserId(userId);
        info.setIdCard("1334qwe");

        return info;
    }

}
