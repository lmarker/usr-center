package com.maksdu.usr.center.server.controller;

import com.maksdu.usr.center.server.authentication.WeChatUsrAuth;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author lijiahao
 * @since 2020/5/7
 */
public abstract class BaseController {

    public WeChatUsrAuth getSecurity() {
        return (WeChatUsrAuth) SecurityContextHolder.getContext().getAuthentication();
    }

    public Long getUserId() {
        return getSecurity().getDetail().getUserId();
    }

    public String getUserName() {
        return getSecurity().getDetail().getNickName();
    }



}
