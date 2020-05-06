package com.maksdu.usr.center.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.ibatis.type.Alias;

@Data
@EqualsAndHashCode(callSuper = true)
@Alias("role")
public class WeChatUserRoleDO extends BaseDO{

    private Long groupId;

    private String roleName;

    private Integer enable;
}
