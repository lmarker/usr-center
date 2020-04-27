package com.maksdu.usr.center.core.proxy.feign;

import com.maksdu.usr.center.core.proxy.dto.WeChatAccessTokenDTO;
import com.maksdu.usr.center.core.proxy.dto.WeChatJsCode2SessionDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name="we-chat", url= "${weChat.api.url}")
public interface WeChatFeign {

    /**
     * 获取临时凭证
     */
    @GetMapping("/cgi-bin/token")
    WeChatAccessTokenDTO getAccessToken(
            @RequestParam String code,
            @RequestParam String appid,
            @RequestParam String secret,
            @RequestParam String grant_type
    );

    /**
     */
    @GetMapping("sns/jscode2session")
    WeChatJsCode2SessionDTO getJscode2session(
            @RequestParam String appid,
            @RequestParam String secret,
            @RequestParam String js_code,
            @RequestParam String grant_type
    );




}
