package com.maksdu.usr.center.service;

import com.maksdu.usr.center.core.proxy.utils.IdContextGeneratorHolder;
import com.maksdu.usr.center.dao.*;
import com.maksdu.usr.center.domain.WeChatUserRoleDO;
import com.maksdu.usr.center.domain.WeChatUserRoleGroupDO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;

@Service
@AllArgsConstructor
public class WeChatUsrManagerServiceImpl implements WeChatUsrManagerService {

    private final WeChatUserStatusDAO weChatUserStatusDAO;

    private final WeChatUserRoleDAO weChatUserRoleDAO;

    private final WeChatUserRoleGroupDAO weChatUserRoleGroupDAO;

    private final WeChatUserAuthorityDAO weChatUserAuthorityDAO;

    @Override
    @Transactional
    public void createGroup(String groupName, Long userId, String userName) {
        WeChatUserRoleGroupDO group = new WeChatUserRoleGroupDO();
        group.setCode(IdContextGeneratorHolder.generatorId(()->"GRU"));
        group.setGroupName(groupName);
        group.setCreatorId(userId);
        group.setCreatorName(userName);
        Assert.isTrue(weChatUserRoleGroupDAO.insert(group) > 0, "用户组创建失败");
    }

    @Override
    public void addGroupRole(String roleName, Long groupId) {
        WeChatUserRoleDO item = WeChatUserRoleDO.builder()
                .groupId(groupId)
                .roleName(roleName).build();
        item.setCode(IdContextGeneratorHolder.generatorId(()->"ROL"));
        Assert.isTrue(weChatUserRoleDAO.insert(item) > 0, "用户角色添加失败");
    }

    @Override
    public List<WeChatUserRoleGroupDO> findGroups() {
        return weChatUserRoleGroupDAO.findAll(null);
    }

    @Override
    public List<WeChatUserRoleDO> findRoleByGroupId(Long groupId) {
        return weChatUserRoleDAO.findByGroupId(groupId);
    }


}
