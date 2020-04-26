package com.maksdu.usr.center.server.authentication;

import com.maksdu.usr.center.core.proxy.common.ResultCode;
import com.maksdu.usr.center.core.proxy.exception.BizException;
import com.maksdu.usr.center.server.utils.WeChatUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Slf4j
public class WeChatAuthenticationFilter extends OncePerRequestFilter {

    private final CustomUserDetailsService userDetailsService;

    public WeChatAuthenticationFilter(CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader(WeChatUtils.TOKEN_ACCESS);
        log.info("## authHeader={}", authHeader);

        if (authHeader != null) {

            if (StringUtils.isEmpty(authHeader) || !authHeader.startsWith(WeChatUtils.TOKEN_PREFIX)) {
                log.info("### 用户未登录，请先登录 ### token: {}", authHeader);
            }

            log.info("正在进行token 验证 --- {}", authHeader);
            // 获取token
            authHeader = authHeader.substring(WeChatUtils.TOKEN_PREFIX.length());
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if(authentication == null || authentication.getDetails() == null) {
                //TODO 根据authHeader 计算出openId
                String openId = request.getHeader(WeChatUtils.OPENID);
                log.info("openId :{}", openId);
                UserDetails userDetails = userDetailsService.loadUserByUsername(openId);
                if(userDetails != null) {
                    authentication = new WeChatUsrAuth(authHeader, userDetails, openId);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        }

        filterChain.doFilter(request, response);
    }
}
