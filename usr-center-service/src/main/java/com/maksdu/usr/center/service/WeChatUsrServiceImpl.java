package com.maksdu.usr.center.service;

import com.maksdu.usr.center.dao.WeChatUserInfoDAO;
import com.maksdu.usr.center.domain.WeChatUserDetailsDO;
import com.maksdu.usr.center.service.dto.UserInfo;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
@AllArgsConstructor
public class WeChatUsrServiceImpl extends BaseService implements WeChatUsrService {

    private final WeChatUserInfoDAO weChatUserInfoDAO;

    @Override
    public void storageUserInfo(UserInfo userInfo) {
        WeChatUserDetailsDO details = new WeChatUserDetailsDO();
        BeanUtils.copyProperties(userInfo, details);
        weChatUserInfoDAO.insert(details);
    }

    @Override
    public WeChatUserDetailsDO getUserInfoByOpenId(String openId) {
        return weChatUserInfoDAO.getByOpenId(openId);
    }
}
