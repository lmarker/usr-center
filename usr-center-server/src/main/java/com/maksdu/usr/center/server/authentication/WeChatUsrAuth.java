package com.maksdu.usr.center.server.authentication;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class WeChatUsrAuth implements Authentication {

    //第三方的key
    private String sessionKey;

    //用户密码信息
    //微信登录不包含密码
    private UserDetails userDetails;

    //用户具体信息
    private String openId;

    private boolean isAuthentication;

    private Set<GrantedAuthority> authoritySet;

    public WeChatUsrAuth(String sessionKey, UserDetails userDetails, String openId) {
        this.sessionKey = sessionKey;
        this.userDetails = userDetails;
        this.openId = openId;
        this.authoritySet = new HashSet<>();

    }

    public WeChatUsrAuth(String sessionKey, UserDetails userDetails, String openId, Set<GrantedAuthority> defaultAuthorities) {
        this.sessionKey = sessionKey;
        this.userDetails = userDetails;
        this.openId = openId;
        this.authoritySet = defaultAuthorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        authoritySet.addAll(userDetails.getAuthorities());
        return authoritySet;
    }

    @Override
    public Object getCredentials() {
        return sessionKey;
    }

    @Override
    public Object getDetails() {
        return openId;
    }

    @Override
    public Object getPrincipal() {
        return userDetails;
    }

    @Override
    public boolean isAuthenticated() {
        if(userDetails == null) {
            return isAuthentication;
        } else {
            if(userDetails instanceof WeChatUsrAuth) {
                return isAuthentication
                        && userDetails.isEnabled()
                        && userDetails.isAccountNonLocked()
                        && userDetails.isAccountNonExpired()
                        && userDetails.isCredentialsNonExpired();
            } else {
                return isAuthentication;
            }
        }
    }

    @Override
    public void setAuthenticated(boolean isAuthentication) throws IllegalArgumentException {
        this.isAuthentication = isAuthentication;
    }

    @Override
    public String getName() {
        return openId;
    }
}
