package com.r2d2.api.service;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * Created by ch on 2018/1/20.
 */
@Getter
@Setter
@ToString
public class UserInfo {
    private String name;
    private String sex;
    private Long userId;
    private String idCard;
    private Date date;
}
