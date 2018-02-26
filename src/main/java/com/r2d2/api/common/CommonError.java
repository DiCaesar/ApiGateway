package com.r2d2.api.common;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * Created by cheng on 2018/2/26.
 */
@Getter
@Setter
@ToString
public class CommonError implements Serializable {

    private String errorCode;

    private String errorDesc;

    public CommonError(String errorCode,String errorDesc){
        this.errorCode = errorCode;
        this.errorDesc = errorDesc;
    }
}
