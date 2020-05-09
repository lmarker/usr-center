package com.maksdu.usr.center.core.proxy.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class WeChatJsCode2SessionDTO implements Serializable {

    private String openid;

    private String sessionKey;

    private String localToken;

    private Boolean isNotFirstLogin = true;
}
