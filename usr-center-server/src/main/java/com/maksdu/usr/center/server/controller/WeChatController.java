package com.maksdu.usr.center.server.controller;

import com.maksdu.usr.center.core.proxy.dto.WeChatAccessTokenDTO;
import com.maksdu.usr.center.core.proxy.dto.WeChatJsCode2SessionDTO;
import com.maksdu.usr.center.core.proxy.feign.WeChatFeign;
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
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/wx")
@Slf4j
public class WeChatController {

    private final WeChatFeign weChatFeign;

    private final WeChatUsrService weChatUsrService;

    @Value("wx97557af143923e7b")
    private String appId;

    @Value("ae80268760c8a5fba9a896b6e9430f44")
    private String appSecret;

    @Value("authorization_code")
    private String grantType;

    public WeChatController(WeChatFeign weChatFeign,WeChatUsrService weChatUsrService) {
        this.weChatFeign = weChatFeign;
        this.weChatUsrService = weChatUsrService;
    }

    /** 接口凭证 ,返回的是临时登录 token **/
    @PostMapping("/login")
    public WeChatAccessTokenDTO getAccessTokenDTO(@RequestBody AccessTokenParam param) {
        return weChatFeign.getAccessToken(param.getCode(), appId, appSecret, "client_credential");
    }

    /** 用户会话 ,返回的是用户的openId 即唯一标识 **/
    @PostMapping("/session")
    public WeChatJsCode2SessionDTO getJsCode2SessionDTO(@RequestBody AccessTokenParam param) {
        WeChatJsCode2SessionDTO weChatJsCode2SessionDTO =
                weChatFeign.getJscode2session(appId, appSecret, param.getCode(), grantType);
        //存入redis 作为 key:
        String sessionKey = weChatJsCode2SessionDTO.getSessionKey();
        //存入redis 作为 value:
        String openid = weChatJsCode2SessionDTO.getOpenid();
        UserDetails details = detailsHandle(openid);

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
    public void getUserInfo(@RequestHeader String openId, @RequestBody UserInfo userInfo) {
        //TODO 获取前端传递过来的用户信息可以进行持久化
        log.info("get userInfo:{} openId:{}",userInfo, openId);
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        if(StringUtils.pathEquals(name, openId)) {
            weChatUsrService.storageUserInfo(userInfo);
        }
    }

    @Data
    private static class AccessTokenParam {
        private String code;
    }

    private UserDetails detailsHandle(String openId) {
        //校验用户openId 用户是否被锁定，身份认证是否超时
        return null;
    }

}
