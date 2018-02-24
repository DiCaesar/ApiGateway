package com.r2d2.api.core;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Administrator on 2018/1/20.
 */
public class ApiGatewayServlet extends HttpServlet {

    private ApplicationContext context;

    private ApiGatewayHand apiHand;

    @Override
    public void init() throws ServletException{
        super.init();
        context = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
        apiHand = context.getBean(ApiGatewayHand.class);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException{
        apiHand.handle(request,response);
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException{
        System.out.println("========"+request.toString());
        apiHand.handle(request,response);
    }
}
