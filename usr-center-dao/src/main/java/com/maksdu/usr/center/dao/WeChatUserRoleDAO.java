package com.maksdu.usr.center.dao;

import com.maksdu.usr.center.domain.WeChatUserRoleDO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WeChatUserRoleDAO extends BaseDAO<WeChatUserRoleDO, Long> {

    List<WeChatUserRoleDO> findByGroupId(Long groupId);
}
