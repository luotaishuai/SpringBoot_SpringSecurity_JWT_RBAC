package com.cun.security3.config;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.Set;

@Component("rbacauthorityservice")
public class RbacAuthorityService {
    public boolean hasPermission(HttpServletRequest request, Authentication authentication) {

        Object userInfo = authentication.getPrincipal();

        boolean hasPermission  = false;

        if (userInfo instanceof UserDetails) {

            String username = ((UserDetails) userInfo).getUsername();

            // TODO: 2019/4/9 获取username对应的permission表中的url，以下为简易版
            //获取资源
            Set<String> urls = new HashSet();
            urls.add("/common/**"); // 这些 url 都是要登录后才能访问，且其他的 url 都不能访问！
            Set set2 = new HashSet();
            Set set3 = new HashSet();

            AntPathMatcher antPathMatcher = new AntPathMatcher();

            for (String url : urls) {
                String x = request.getRequestURI();
                if (antPathMatcher.match(url, request.getRequestURI())) {
                    hasPermission = true;
                    break;
                }
            }

            return hasPermission;
        } else {
            return false;
        }
    }
}
