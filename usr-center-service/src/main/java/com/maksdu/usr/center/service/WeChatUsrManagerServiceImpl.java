package com.maksdu.usr.center.service;

import com.maksdu.usr.center.dao.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class WeChatUsrManagerServiceImpl implements WeChatUsrManagerService {

    private final WeChatUserStatusDAO weChatUserStatusDAO;

    private final WeChatUserRoleDAO weChatUserRoleDAO;

    private final WeChatUserRoleGroupDAO weChatUserRoleGroupDAO;

    private final WeChatUserAuthorityDAO weChatUserAuthorityDAO;

}
