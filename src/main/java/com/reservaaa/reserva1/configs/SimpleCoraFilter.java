package com.reservaaa.reserva1.configs;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class SimpleCoraFilter implements Filter {
   public SimpleCoraFilter(){

   }


@Override
public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {

    HttpServletResponse response= (HttpServletResponse) res;
    HttpServletRequest request= (HttpServletRequest) req;
    Map<String,String> map=new HashMap<>();
    String originHeader=request.getHeader("origin");
    response.setHeader("Access-control-Allow-origin" ,originHeader);
    response.setHeader("Access-control-Allow-methods" ,"POST,GET,PUT,OPTIONS,DELETE");
    response.setHeader("Access-control-MAX-AGE" ,"3600");
    response.setHeader("Access-control-Allow-headers" ,"*");

    if("OPTIONS".equalsIgnoreCase(request.getMethod())){
        response.setStatus(HttpServletResponse.SC_OK);
    }else{
       chain.doFilter(req,res);
    }
}
}