package com.maksdu.usr.center.server.controller;

import com.maksdu.usr.center.core.proxy.dto.WeChatAccessTokenDTO;
import com.maksdu.usr.center.core.proxy.feign.WeChatFeign;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/wx")
@AllArgsConstructor
public class WeChatController {

    private final WeChatFeign weChatFeign;

    @GetMapping("/login")
    public WeChatAccessTokenDTO getAccessTokenDTO() {
        return weChatFeign.getAccessToken();
    }

}
