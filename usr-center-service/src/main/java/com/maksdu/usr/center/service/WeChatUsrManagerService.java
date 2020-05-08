package com.maksdu.usr.center.service;


import com.maksdu.usr.center.domain.WeChatUserRoleDO;
import com.maksdu.usr.center.domain.WeChatUserRoleGroupDO;

import java.util.List;

public interface WeChatUsrManagerService {

    void createGroup(String groupName, Long userId, String nickName);

    void addGroupRole(String roleName, Long groupId);

    List<WeChatUserRoleGroupDO> findGroups();

    List<WeChatUserRoleDO> findRoleByGroupId(Long groupId);
}
