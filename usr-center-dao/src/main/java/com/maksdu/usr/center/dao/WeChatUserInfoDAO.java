package com.maksdu.usr.center.dao;

import com.maksdu.usr.center.domain.WeChatUserDetailsDO;
import org.springframework.stereotype.Repository;

@Repository
public interface WeChatUserInfoDAO extends BaseDAO<WeChatUserDetailsDO, Long> {

    WeChatUserDetailsDO getByOpenId(String openId);

}
