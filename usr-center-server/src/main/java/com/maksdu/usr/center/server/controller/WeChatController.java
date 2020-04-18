package com.maksdu.usr.center.server.controller;

import com.maksdu.usr.center.core.proxy.dto.WeChatAccessTokenDTO;
import com.maksdu.usr.center.core.proxy.feign.WeChatFeign;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/wx")
public class WeChatController {

    private final WeChatFeign weChatFeign;

    @Value("wx97557af143923e7b")
    private String appId;

    @Value("ae80268760c8a5fba9a896b6e9430f44")
    private String appSecret;

    @Value("client_credential")
    private String grantType;

    public WeChatController(WeChatFeign weChatFeign) {
        this.weChatFeign = weChatFeign;
    }

    @PostMapping("/login")
    public WeChatAccessTokenDTO getAccessTokenDTO(@RequestBody AccessTokenParam param) {
        return weChatFeign.getAccessToken(param.getCode(), appId, appSecret, grantType);
    }

    @Data
    private static class AccessTokenParam {
        private String code;
    }

}
