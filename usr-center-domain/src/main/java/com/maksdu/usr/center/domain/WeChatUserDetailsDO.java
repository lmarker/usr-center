package com.maksdu.usr.center.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class WeChatUserDetailsDO extends BaseDO {

    private String openId;

    private String nickName;

    private Integer gender;

    private String city;

    private String province;

    private String country;

    private String avatarUrl;

}