package com.maksdu.usr.center.server.controller;

import com.maksdu.usr.center.core.proxy.dto.WeChatJsCode2SessionDTO;
import com.maksdu.usr.center.core.proxy.feign.WeChatFeign;
import com.maksdu.usr.center.domain.WeChatUserDetailsDO;
import com.maksdu.usr.center.server.authentication.CustomUserDetailsService;
import com.maksdu.usr.center.server.authentication.UserDetail;
import com.maksdu.usr.center.server.authentication.User;
import com.maksdu.usr.center.server.utils.JwtUtils;
import com.maksdu.usr.center.service.WeChatUsrService;
import com.maksdu.usr.center.service.dto.UserInfo;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/wx")
@Slf4j
public class WeChatController {

    private final WeChatFeign weChatFeign;

    private final WeChatUsrService weChatUsrService;

    private final CustomUserDetailsService customUserDetailsService;

    private final JwtUtils jwtUtils;

    @Value("wx97557af143923e7b")
    private String appId;

    @Value("ae80268760c8a5fba9a896b6e9430f44")
    private String appSecret;

    @Value("authorization_code")
    private String grantType;

    public WeChatController(WeChatFeign weChatFeign,
                            WeChatUsrService weChatUsrService,
                            CustomUserDetailsService customUserDetailsService,
                            JwtUtils jwtUtils) {
        this.weChatFeign = weChatFeign;
        this.weChatUsrService = weChatUsrService;
        this.customUserDetailsService = customUserDetailsService;
        this.jwtUtils = jwtUtils;
    }

    /** 用户会话 ,返回的是用户的openId 即唯一标识 **/
    @PostMapping("/login")
    public WeChatJsCode2SessionDTO getJsCode2SessionDTO(@RequestBody AccessTokenParam param) {
        //TODO 不应该返回session_key 给小程序端，因为session_key是服务端与微信交互的凭证
        //     可以用jwt作为小程序端与服务端的token凭证，session_key以openId为key, 保存到redis中
        WeChatJsCode2SessionDTO weChatJsCode2SessionDTO =
                weChatFeign.getJscode2session(appId, appSecret, param.getCode(), grantType);
        String openid = weChatJsCode2SessionDTO.getOpenid();
        UserDetail details = (UserDetail) customUserDetailsService.loadUserByUsername(openid);
        if(details == null) {
            weChatJsCode2SessionDTO.setIsNotFirstLogin(false);
        } else {
            weChatJsCode2SessionDTO.setLocalToken(jwtUtils.generateAccessToken(details));
        }
        return weChatJsCode2SessionDTO;
    }

    /**
     * 用于第一次登录时，检查数据库没有该用户信息，通过返回isNotFirstLogin = false 来触发
     */
    @PostMapping("/first_login")
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public void getUserInfo(@RequestHeader String openid, @RequestBody UserInfo userInfo) {
        //TODO 获取前端传递过来的用户信息可以进行持久化
        WeChatUserDetailsDO details = weChatUsrService.getUserInfoByOpenId(openid);
        if(details == null) {
            weChatUsrService.storageUserInfo(userInfo, openid);
            details = weChatUsrService.getUserInfoByOpenId(openid);
        }
        UserDetail principal = new UserDetail(details);
        Authentication authentication = new User(principal);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Data
    private static class AccessTokenParam {
        private String code;
    }

}
