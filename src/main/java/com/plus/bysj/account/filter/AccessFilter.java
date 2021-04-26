package com.plus.bysj.account.filter;

/**
 * @ProjectName: account
 * @Package: com.plus.bysj.account.filter
 * @ClassName: AccessFilter
 * @Author: rh
 * @Description:
 * @Date: 2021/4/23 16:31
 */

import com.alibaba.fastjson.JSON;
import com.plus.bysj.account.beans.QuickRequestWapper;
import com.plus.bysj.account.beans.TokenInfo;
import com.plus.bysj.account.config.FilterUrlsConfig;
import com.plus.bysj.account.exce.ServiceException;
import com.plus.bysj.account.utils.AESUtil;
import com.plus.bysj.account.utils.AntPathMatcherUtils;
import com.plus.bysj.account.utils.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.rmi.ServerException;

/**
 * 资源过滤器
 * 所有的资源请求在路由之前进行前置过滤
 * 如果请求头不包含 Authorization参数值，直接拦截不再路由
 * @author root
 */
public class AccessFilter implements Filter {

    @Autowired
    private RedisUtil redisUtil;

    private static final String BEARER_TOKEN_TYPE = "Bearer ";

    private static Logger log = LoggerFactory.getLogger(AccessFilter.class);

    @Autowired
    FilterUrlsConfig filterUrlsConfig;

    /**
     * 过滤器是否会被执行
     *
     * @return true
     */
    public boolean shouldFilter(HttpServletRequest request) {
        //1获取当前用户身份信息
        boolean should = true;
        String requestURI = request.getRequestURI();
        //无需认证的URL
        if (AntPathMatcherUtils.match(filterUrlsConfig.getAnon(), requestURI)) {
            should = false;
        }
        log.info("send {} request to {} should {}", request.getMethod(), request.getRequestURL().toString(), should);
        return should;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Methods", "GET, HEAD, POST, PUT, PATCH, DELETE, OPTIONS");
        response.setHeader("Access-Control-Max-Age", "86400");
        //response.setHeader("Access-Control-Allow-Headers", "Authorization");
        response.setHeader("Access-Control-Allow-Headers", "Authorization,Content-Type,X-CAF-Authorization-Token,sessionToken,X-TOKEN");
        log.info("send {} request to {}", request.getMethod(), request.getRequestURL().toString());
        // 如果是OPTIONS请求则结束
        if (HttpMethod.OPTIONS.toString().equals(request.getMethod())) {
            response.getWriter().println("ok");
            return;
        }

        if(shouldFilter(request)){
            log.error("拦截");
            String authToken = extractToken(request);
            if(StringUtils.isEmpty(authToken)){
                throw new ServiceException("-TOKEN","未登录");
            }
            String token = authToken.replace(BEARER_TOKEN_TYPE,"");
            if(StringUtils.isEmpty(token)){
                throw new ServiceException("-TOKEN","未登录");
            }else {
               String str =  AESUtil.aesDecrypt(token);
               if(StringUtils.equals(str,token)){
                   throw new ServiceException("-TOKEN","未登录");
               }else {
                   TokenInfo info = JSON.parseObject(str,TokenInfo.class);
                   String userId = info.getUserId();
                   if(redisUtil.hasKey(userId)) {
                       String redisToken = redisUtil.get(userId).toString();
                       if(!StringUtils.equals(token,redisToken)){
                           throw new ServiceException( "-TOKEN","已在其他地方登录");
                       }else {
                           QuickRequestWapper quickRequestWapper = new QuickRequestWapper(request,info,userId);
                           filterChain.doFilter(quickRequestWapper,servletResponse);
                       }
                   }else {
                       throw new ServiceException( "-TOKEN","登录已过期");
                   }
               }
            }
        }else {
            filterChain.doFilter(servletRequest,servletResponse);
        }
    }

    @Override
    public void destroy() {

    }

    /**
     * 重组请求头Authorization
     */
    protected String extractToken(HttpServletRequest request) {
        String authToken = "";
        String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (!StringUtils.isEmpty(authorization)) {
            authToken = authorization.trim();
        }
        if (StringUtils.isEmpty(authToken)) {
            authToken = request.getParameter("access_token");
            if (StringUtils.isNotEmpty(authToken)) {
                authToken = BEARER_TOKEN_TYPE + authToken;
            }
        }
        return authToken;
    }
}

