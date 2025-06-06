package com.example.interceptor;

import java.util.Map;

import org.springframework.core.annotation.Order;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import com.alibaba.fastjson2.JSONObject;
import com.example.pojo.Result;
import com.example.utils.JwtUtils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@Order(1)
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
            @NonNull Object handler)
            throws Exception {

        String jws = request.getHeader("Token");

        if (!StringUtils.hasText(jws)) {
            log.info("令牌不存在");
            String msg = JSONObject.toJSONString(Result.error("NOT_LOGIN"));
            response.getWriter().write(msg);
            return false;
        }

        try {
            Map<String, Integer> map = JwtUtils.parseToken(jws);
            request.setAttribute("id", map.get("id"));
            request.setAttribute("role", map.get("role"));
        } catch (Exception e) {
            log.info("令牌无效");
            String msg = JSONObject.toJSONString(Result.error("NOT_LOGIN"));
            response.getWriter().write(msg);
            return false;
        }

        log.info("令牌验证成功");
        return true;
    }
}
