package com.maksdu.usr.center.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.ibatis.type.Alias;

@Data
@EqualsAndHashCode(callSuper = true)
@Alias("roleGroup")
public class WeChatUserRoleGroupDO extends BaseDO {

    private String groupName;

    private Boolean usable;

}
