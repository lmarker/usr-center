package com.maksdu.usr.center.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.ibatis.type.Alias;

@Data
@EqualsAndHashCode(callSuper = true)
@Alias("roleGroup")
public class WeChatUserRoleGroupDO extends BaseDO {

    private String groupName;

    private Long creatorId;

    private String creatorName;

    private Integer enable;

}
