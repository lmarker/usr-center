package com.maksdu.usr.center.server.controller;

import com.maksdu.usr.center.core.proxy.dto.WeChatAccessTokenDTO;
import com.maksdu.usr.center.core.proxy.dto.WeChatJsCode2SessionDTO;
import com.maksdu.usr.center.core.proxy.feign.WeChatFeign;
import com.maksdu.usr.center.server.authentication.CustomUserDetailsService;
import com.maksdu.usr.center.server.authentication.WeChatUsrAuth;
import com.maksdu.usr.center.service.WeChatUsrService;
import com.maksdu.usr.center.service.dto.UserInfo;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/wx")
@Slf4j
public class WeChatController {

    private final WeChatFeign weChatFeign;

    private final WeChatUsrService weChatUsrService;

    private final CustomUserDetailsService customUserDetailsService;

    @Value("wx97557af143923e7b")
    private String appId;

    @Value("ae80268760c8a5fba9a896b6e9430f44")
    private String appSecret;

    @Value("authorization_code")
    private String grantType;

    public WeChatController(WeChatFeign weChatFeign, WeChatUsrService weChatUsrService, CustomUserDetailsService customUserDetailsService) {
        this.weChatFeign = weChatFeign;
        this.weChatUsrService = weChatUsrService;
        this.customUserDetailsService = customUserDetailsService;
    }

    /** 接口凭证 ,返回的是临时登录 token **/
    @PostMapping("/login")
    public WeChatAccessTokenDTO getAccessTokenDTO(@RequestBody AccessTokenParam param) {
        return weChatFeign.getAccessToken(param.getCode(), appId, appSecret, "client_credential");
    }

    /** 用户会话 ,返回的是用户的openId 即唯一标识 **/
    @PostMapping("/session")
    public WeChatJsCode2SessionDTO getJsCode2SessionDTO(@RequestBody AccessTokenParam param) {
        //TODO 不应该返回session_key 给小程序端，因为session_key是服务端与微信交互的凭证
        //     可以用jwt作为小程序端与服务端的token凭证，session_key以openId为key, 保存到redis中
        WeChatJsCode2SessionDTO weChatJsCode2SessionDTO =
                weChatFeign.getJscode2session(appId, appSecret, param.getCode(), grantType);
        //存入redis 作为 key:
        String sessionKey = weChatJsCode2SessionDTO.getSessionKey();
        //存入redis 作为 value:
        String openid = weChatJsCode2SessionDTO.getOpenid();
        UserDetails details = customUserDetailsService.loadUserByUsername(openid);

        if(details == null) {
            weChatJsCode2SessionDTO.setIsNotFirstLogin(false);
        }
        //认证对象
        Authentication weChatUsrAuth = new WeChatUsrAuth(
                sessionKey,
                details,
                openid);
        SecurityContextHolder.getContext().setAuthentication(weChatUsrAuth);
        return weChatJsCode2SessionDTO;
    }

    @PostMapping("/user_info")
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public void getUserInfo(@RequestHeader String openid, @RequestBody UserInfo userInfo) {
        //TODO 获取前端传递过来的用户信息可以进行持久化
        log.info("get userInfo:{}",userInfo);
        weChatUsrService.storageUserInfo(userInfo, openid);
    }

    @Data
    private static class AccessTokenParam {
        private String code;
    }

}
