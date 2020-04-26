package com.maksdu.usr.center.server.authentication;

import com.maksdu.usr.center.domain.WeChatUserDetailsDO;
import lombok.Builder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;

@Builder
public class WeChatPrincipal implements UserDetails {

    private String openId;

    private boolean lock;
    private boolean accountExpired;
    private boolean credentialsExpired;
    private boolean enable;

    private WeChatUserDetailsDO entity;

    private Set<GrantedAuthority> authoritySet;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authoritySet;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return openId;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.accountExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.lock;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.credentialsExpired;
    }

    @Override
    public boolean isEnabled() {
        return this.enable;
    }
}