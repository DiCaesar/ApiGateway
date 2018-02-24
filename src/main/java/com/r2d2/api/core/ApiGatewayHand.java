package com.r2d2.api.core;

import com.auth0.jwt.internal.com.fasterxml.jackson.databind.SerializationFeature;
import com.r2d2.api.common.ApiException;
import com.r2d2.api.common.UtilJson;
import com.r2d2.api.service.GoodsServiceImp.Goods;
import com.r2d2.api.core.ApiStore.ApiRunnable;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ch on 2018/1/20.
 */
@Service
public class ApiGatewayHand implements InitializingBean,ApplicationContextAware{
    private static final Logger log = LoggerFactory.getLogger(ApiGatewayHand.class);
    private static final String METHOD = "method";
    private static final String PARAMS = "params";

    private ApiStore apiStore;
    private final ParameterNameDiscoverer parameterNameDiscoverer;


    public ApiGatewayHand(){
        parameterNameDiscoverer = new LocalVariableTableParameterNameDiscoverer();
    }


    public void setApplicationContext(ApplicationContext context) throws BeansException{
        apiStore = new ApiStore(context);
    }

    public void afterPropertiesSet(){
        apiStore.loadApiFromSpringBeans();
    }

    public void handle(HttpServletRequest request,HttpServletResponse response){
        //参数验证
        String method = request.getParameter(METHOD);
        String params = request.getParameter(PARAMS);
        Object result;
        ApiRunnable apiRun;
        try{
            apiRun = sysParamsValidate(request);
            log.info("请求接口:{},参数:{}",method,params);
            Object[] args = buildParams(apiRun,params,request,response);
            result = apiRun.run(args);
        } catch (ApiException e) {
            response.setStatus(500);
            log.error("调用接口:{} 异常,{}",method,e.getMessage());
            result = handleError(e);
        }catch (InvocationTargetException e){
            response.setStatus(500);
            log.error("调用接口异常"+method+"参数"+params+e.getTargetException());
            result = handleError(e.getTargetException());
        }catch (Exception e){
            response.setStatus(500);
            log.error("其他异常"+method+"参数"+params);
            result = handleError(e);
        }

        returnResult(result,response);
    }

    public Object handleError(Throwable throwable){
        return  null;
    }



    public Object[] buildParams(ApiRunnable run, String paramJson, HttpServletRequest request,
                              HttpServletResponse response) throws ApiException{
        Map<String,Object> map = null;
        try{
            map = UtilJson.toMap(paramJson);
        }catch (IllegalArgumentException e){
            throw new ApiException("调用失败：json字符串格式异常，请检查params参数");
        }
        if(map == null){
            map = new HashMap<>();
        }

        Method method =run.getTargetMethod();
        List<String> paramNames = Arrays.asList(parameterNameDiscoverer.getParameterNames(method));

        Class<?>[] paramsTypes = method.getParameterTypes();

        for (Map.Entry<String, Object> m : map.entrySet()) {
            if(!paramNames.contains((m.getKey()))){
                throw new ApiException("调用失败：接口不存在 '"+m.getKey()+"'参数");
            }
        }
        Object[] args = new Object[paramsTypes.length];
        for(int i=0;i<paramsTypes.length; i++){
            if(paramsTypes[i].isAssignableFrom(HttpServletRequest.class)){
                args[i] = request;
            }else if(map.containsKey(paramNames.get(i))){
                try{
                    args[i] = convertJsonToBean(map.get(paramNames.get(i)),paramsTypes[i]);
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        }

        return  null;
    }

    public Object convertJsonToBean(Object obj,Class<?> clazz){
        return null;
    }



    public ApiRunnable sysParamsValidate(HttpServletRequest request) throws ApiException{
        String apiName = request.getParameter(METHOD);
        String  json = request.getParameter(PARAMS);

        ApiRunnable api;
        if(StringUtils.isEmpty(apiName)){
            throw new ApiException("调用失败：参数 method 为空");
        }else if (StringUtils.isEmpty(json)){
            throw new ApiException("调用失败：参数 params 为空");
        }else if((api = apiStore.findApiRunnable(apiName)) == null){
            throw new ApiException("调用失败：指定api不存在 "+apiName);
        }
        return api;
    }

    //205
    private void returnResult(Object result , HttpServletResponse response){
        try{
            UtilJson.JSON_MAPPER.configure(SerializationFeature.WRITE_NULL_MAP_VALUES,true);
            String json = UtilJson.writeValuesAsString(result);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html/json;charset=utf-8");
            response.setHeader("Cache-Control","no-cache");
            response.setHeader("Pragma","no-cache");
            response.setDateHeader("Expires",0);
            if(json != null){
                response.getWriter().write(json);
            }
        } catch (IOException e) {
            System.out.println("服务中心响应异常"+e);
            throw new RuntimeException(e);
        }
    }

    //224
    public static void main(String[] args) {
        String mapString = "{\"goods\":{\"goodsName\":\"nnn\",\"goodsId\":\"123\"},\"id\":457}";
        Map<String,Object> map = UtilJson.toMap(mapString);
        System.out.println(map);
        Goods goods = UtilJson.convertValue(map.get("goods"),Goods.class);
        System.out.println(goods.toString());

        String str = "{\"goodsName\":\"saf\",\"goodsId\":\"254\"}";
        goods = UtilJson.convertValue(str,Goods.class);
        System.out.println(goods.toString());

        String jsonStr = UtilJson.writeValuesAsString(goods);
        System.out.println(jsonStr);
    }

}
