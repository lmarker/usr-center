package com.maksdu.usr.center.service;

import com.maksdu.usr.center.domain.WeChatUserDetailsDO;
import com.maksdu.usr.center.service.dto.UserInfo;
import org.springframework.stereotype.Service;

public interface WeChatUsrService {

    void storageUserInfo(UserInfo userInfo);

    WeChatUserDetailsDO getUserInfoByOpenId(String openId);
}
