package com.maksdu.usr.center.server.controller;

import com.maksdu.usr.center.server.authentication.User;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author lijiahao
 * @since 2020/5/7
 */
public abstract class BaseController extends ApplicationObjectSupport {

    protected User getSecurity() {
        return (User) SecurityContextHolder.getContext().getAuthentication();
    }

    protected Long getUserId() {
        return getSecurity().getDetail().getUserId();
    }

    protected String getUserName() {
        return getSecurity().getDetail().getNickName();
    }

}
