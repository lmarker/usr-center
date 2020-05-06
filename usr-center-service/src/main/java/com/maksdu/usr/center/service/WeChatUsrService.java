package com.maksdu.usr.center.service;

import com.maksdu.usr.center.domain.WeChatUserDetailsDO;
import com.maksdu.usr.center.service.dto.UserInfo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.stereotype.Service;

public interface WeChatUsrService {

    void storageUserInfo(UserInfo userInfo, String openId);

    WeChatUserDetailsDO getUserInfoByOpenId(String openId);
}
