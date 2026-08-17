package com.family.finance.system.common.interceptor;

import com.family.finance.system.common.util.JwtUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AuthInterceptor implements HandlerInterceptor {
    @Resource
    private JwtUtil jwtUtil;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");
        if(token == null || token.isEmpty()){
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write("{\"code\":401,\"msg\":\"未登录，请先登录\"}");
            return false;
        }
        try {
            Long userId = jwtUtil.getUserId(token);
            request.setAttribute("loginUserId",userId);
        }catch (Exception e){
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write("{\"code\":401,\"msg\":\"token失效，请重新登录\"}");
            return false;
        }
        return true;
    }
}