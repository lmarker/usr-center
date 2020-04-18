package com.maksdu.usr.center.server.controller;

import com.maksdu.usr.center.core.proxy.dto.WeChatAccessTokenDTO;
import com.maksdu.usr.center.core.proxy.dto.WeChatJsCode2SessionDTO;
import com.maksdu.usr.center.core.proxy.feign.WeChatFeign;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/wx")
public class WeChatController {

    private final WeChatFeign weChatFeign;

    @Value("wx97557af143923e7b")
    private String appId;

    @Value("ae80268760c8a5fba9a896b6e9430f44")
    private String appSecret;

    @Value("authorization_code")
    private String grantType;

    public WeChatController(WeChatFeign weChatFeign) {
        this.weChatFeign = weChatFeign;
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

    @Data
    private static class AccessTokenParam {
        private String code;
    }

}
