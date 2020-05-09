package com.maksdu.usr.center.server.authentication;

import com.maksdu.usr.center.dao.WeChatUserInfoDAO;
import com.maksdu.usr.center.domain.WeChatUserDetailsDO;
import com.maksdu.usr.center.domain.WeChatUserRoleDO;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Data
@NoArgsConstructor
public class WeChatPrincipal implements UserDetails {

    private Long userId;
    private String openId;
    private String nickName;
    private String code;

    private boolean isLock;
    private boolean isAccountExpired;
    private boolean isCredentialsExpired;
    private boolean isEnable;

    private Set<GrantedAuthority> authoritySet;

    public WeChatPrincipal(WeChatUserDetailsDO weChatUserDetailsDO) {
        this.userId = weChatUserDetailsDO.getId();
        this.openId = weChatUserDetailsDO.getOpenId();
        this.nickName = weChatUserDetailsDO.getNickName();
        this.code = weChatUserDetailsDO.getCode();
    }

    public WeChatPrincipal(Long userId, String openId, String nickName, WeChatUserRoleDO weChatUserRoleDO) {
        this.userId = userId;
        this.openId = openId;
        this.nickName = nickName;
        this.authoritySet = Stream.of(weChatUserRoleDO.getRoleName().split(","))
                .map(str-> (GrantedAuthority) () -> str)
                .collect(Collectors.toSet());
        this.isLock = false;
        this.isAccountExpired = false;
        this.isCredentialsExpired = false;
        this.isEnable = true;
    }

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
        return isAccountExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isLock;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isCredentialsExpired;
    }

    @Override
    public boolean isEnabled() {
        return isEnable;
    }
}
