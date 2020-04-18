package com.maksdu.usr.center.server.controller;

import com.maksdu.usr.center.core.proxy.dto.WeChatAccessTokenDTO;
import com.maksdu.usr.center.core.proxy.dto.WeChatJsCode2SessionDTO;
import com.maksdu.usr.center.core.proxy.feign.WeChatFeign;
import com.maksdu.usr.center.service.WeChatUsrService;
import com.maksdu.usr.center.service.dto.UserInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
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

    /** 接口凭证 **/
    @PostMapping("/login")
    public WeChatAccessTokenDTO getAccessTokenDTO(@RequestBody AccessTokenParam param) {
        return weChatFeign.getAccessToken(param.getCode(), appId, appSecret, "client_credential");
    }

    /** 用户会话 **/
    @PostMapping("/session")
    public WeChatJsCode2SessionDTO getJsCode2SessionDTO(@RequestBody AccessTokenParam param) {
        return weChatFeign.getJscode2session(appId, appSecret, param.getCode(), grantType);
    }

    @PostMapping("/user_info")
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public void getUserInfo(@RequestBody UserInfo userInfo) {
        //TODO 获取前端传递过来的用户信息可以进行持久化
        log.info("get userInfo:{}",userInfo);
        weChatUsrService.storageUserInfo(userInfo);
    }

    @Data
    private static class AccessTokenParam {
        private String code;
    }

}
