package com.maksdu.usr.center.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.ibatis.type.Alias;

/**
 * 用户状态表
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Alias("userStatus")
public class WeChatUserStatusDO extends BaseDO {

    private String openId;

    private Boolean lock;

    private Boolean enable;

    private Long accountExpired;

    private Long credentialsExpired;

}
