package com.maksdu.usr.center.server.authentication;

import lombok.Data;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Data
public class WeChatUsrAuth implements Authentication {


    //用户密码信息
    //微信登录不包含密码
    private WeChatPrincipal detail;

    private boolean isAuthentication;

    private Collection<? extends GrantedAuthority> authoritySet;

    public WeChatUsrAuth(WeChatPrincipal userDetails) {
        this.detail = userDetails;
        this.authoritySet = new HashSet<>();

    }

    public WeChatUsrAuth(WeChatPrincipal userDetails, Collection<? extends GrantedAuthority> defaultAuthorities) {
        this.detail = userDetails;
        this.authoritySet = defaultAuthorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authoritySet;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return detail;
    }

    @Override
    public boolean isAuthenticated() {
//        if(detail == null) {
//            return isAuthentication;
//        } else {
//            return isAuthentication
//                    && getName() != null
//                    && detail.isEnabled()
//                    && !detail.isAccountNonLocked()
//                    && !detail.isAccountNonExpired()
//                    && !detail.isCredentialsNonExpired();
//        }
        return true;
    }

    @Override
    public void setAuthenticated(boolean isAuthentication) throws IllegalArgumentException {
        this.isAuthentication = isAuthentication;
    }

    @Override
    public String getName() {
        return detail != null ? detail.getNickName() : null;
    }
}
