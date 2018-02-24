package com.r2d2.api.common;

import com.auth0.jwt.internal.com.fasterxml.jackson.core.JsonProcessingException;
import com.auth0.jwt.internal.com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.Map;

/**
 * Created by Administrator on 2018/1/20.
 */
public class UtilJson {

    public static final ObjectMapper JSON_MAPPER = new ObjectMapper();


    public static String writeValuesAsString(Object obj){
        if(null == obj) return null;

        try {
            return JSON_MAPPER.writeValueAsString(obj);
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalArgumentException();
        }
    }

    public static Map<String,Object> toMap(String jsonStr){
        if(StringUtils.isEmpty(jsonStr))   return null;
        try {
            return JSON_MAPPER.readValue(jsonStr,Map.class);
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalArgumentException();
        }
    }

    public static <T> T convertValue(Object value,Class<T> clazz) throws IllegalArgumentException{
        if(StringUtils.isEmpty(value))   return null;

        try {
            if(value instanceof String){
                value = JSON_MAPPER.readTree((String) value);
            }
            return JSON_MAPPER.convertValue(value,clazz);
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalArgumentException();
        }
    }
}
