package com.maksdu.usr.center.core.proxy.feign;

import com.maksdu.usr.center.core.proxy.dto.WeChatAccessTokenDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;


@FeignClient(name="we-chat", url= "${weChat.api.url}")
public interface WeChatFeign {


    @GetMapping("/cgi-bin/token?grant_type=client_credential&appid=wx992824f2e4c7489e&secret=8393e93eb9472e5006a51a08ec6da624")
    WeChatAccessTokenDTO getAccessToken();
}
