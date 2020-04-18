package com.maksdu.usr.center.core.proxy.feign;

import com.maksdu.usr.center.core.proxy.dto.WeChatAccessTokenDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name="we-chat", url= "${weChat.api.url}")
public interface WeChatFeign {

    @GetMapping("/cgi-bin/token")
    WeChatAccessTokenDTO getAccessToken(
            @RequestParam String code,
            @RequestParam String appid,
            @RequestParam String secret,
            @RequestParam String grant_type
    );
}
