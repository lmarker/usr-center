package com.maksdu.usr.center.server.authentication;

import com.maksdu.usr.center.core.proxy.common.ResultCode;
import com.maksdu.usr.center.core.proxy.exception.BizException;
import com.maksdu.usr.center.domain.WeChatUserDetailsDO;
import com.maksdu.usr.center.service.WeChatUsrService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    //TODO 使用redis把信息缓存起来，不用每次去数据库查
    private final WeChatUsrService weChatUsrService;

    @Override
    public UserDetails loadUserByUsername(String openid) throws UsernameNotFoundException {
        //根据openid 获取userInfo;
        return convert(weChatUsrService.getUserInfoByOpenId(openid));
    }

    private WeChatPrincipal convert(WeChatUserDetailsDO weChatUserDetailsDO) {
        if(weChatUserDetailsDO == null) {
            return null;
        }
        return WeChatPrincipal.builder()
                .entity(weChatUserDetailsDO)
                .openId(weChatUserDetailsDO.getOpenId())
                .build();
    }

}
