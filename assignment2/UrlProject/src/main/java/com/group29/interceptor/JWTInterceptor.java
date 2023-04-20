package com.group29.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.group29.jwt.JWTUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ryyyyyy
 * @create 2023-04-19 6:49
 *
 * the interceptor to forbid the visit from unauthorized user
 */
public class JWTInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println(request.getRequestURL());
        Map<String,Object> map = new HashMap<String,Object>();
        String token = request.getHeader("token");
        System.out.println(token);
        if (JWTUtils.verify(token)){
            return true;
        }
        map.put("state",false);
        map.put("msg","forbidden!");

        String json = new ObjectMapper().writeValueAsString(map);
        //set the http response code to 403
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().println(json);
        return false;
    }
}
