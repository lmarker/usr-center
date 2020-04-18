package com.maksdu.usr.center.core.proxy.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class WeChatAccessTokenDTO {

    private String accessToken;

    private Long expiresIn;

}
