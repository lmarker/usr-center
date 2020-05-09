package com.maksdu.usr.center.server.controller;

import com.maksdu.usr.center.server.authentication.WeChatUsrAuth;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author lijiahao
 * @since 2020/5/7
 */
public abstract class BaseController extends ApplicationObjectSupport {

    protected WeChatUsrAuth getSecurity() {
        return (WeChatUsrAuth) SecurityContextHolder.getContext().getAuthentication();
    }

    protected Long getUserId() {
        return getSecurity().getDetail().getUserId();
    }

    protected String getUserName() {
        return getSecurity().getDetail().getNickName();
    }

}
