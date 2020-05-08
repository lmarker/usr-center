package com.maksdu.usr.center.server.authentication;

import lombok.Data;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Data
public class WeChatUsrAuth implements Authentication {

    //第三方的key
    private String sessionKey;

    //用户密码信息
    //微信登录不包含密码
    private WeChatPrincipal detail;

    //用户具体信息
    private String openId;

    private boolean isAuthentication;

    private Set<GrantedAuthority> authoritySet;

    public WeChatUsrAuth(String sessionKey, WeChatPrincipal userDetails, String openId) {
        this.sessionKey = sessionKey;
        this.detail = userDetails;
        this.openId = openId;
        this.authoritySet = new HashSet<>();

    }

    public WeChatUsrAuth(String sessionKey, WeChatPrincipal userDetails, String openId, Set<GrantedAuthority> defaultAuthorities) {
        this.sessionKey = sessionKey;
        this.detail = userDetails;
        this.openId = openId;
        this.authoritySet = defaultAuthorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        authoritySet.addAll(detail.getAuthorities());
        return authoritySet;
    }

    @Override
    public Object getCredentials() {
        return sessionKey;
    }

    @Override
    public Object getDetails() {
        return detail;
    }

    @Override
    public Object getPrincipal() {
        return openId;
    }

    @Override
    public boolean isAuthenticated() {
//        if(detail == null) {
//            return isAuthentication;
//        } else {
//            return isAuthentication
//                    && detail.isEnabled()
//                    && detail.isAccountNonLocked()
//                    && detail.isAccountNonExpired()
//                    && detail.isCredentialsNonExpired();
//        }
        return true;
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
