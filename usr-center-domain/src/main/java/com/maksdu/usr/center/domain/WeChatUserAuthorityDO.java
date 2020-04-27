package com.maksdu.usr.center.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.ibatis.type.Alias;

@Data
@EqualsAndHashCode(callSuper = true)
@Alias("authority")
public class WeChatUserAuthorityDO extends BaseDO {

    private String authority;

    private String clients;

}
