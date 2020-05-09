package com.maksdu.usr.center.server.controller;

import com.maksdu.usr.center.domain.WeChatUserRoleDO;
import com.maksdu.usr.center.server.authentication.WeChatUsrAuth;
import com.maksdu.usr.center.service.WeChatUsrManagerService;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author lijiahao
 * @since 2020/5/7
 */
@RestController
@Api(value = "用户权限", tags = {"authority"})
@AllArgsConstructor
@RequestMapping("/auth")
public class AuthorityController extends BaseController {

    private final WeChatUsrManagerService weChatUsrManagerService;

    @PostMapping("/create")
    public void createGroup(String groupName) {
        WeChatUsrAuth authentication = (WeChatUsrAuth) SecurityContextHolder.getContext().getAuthentication();
        weChatUsrManagerService.createGroup(groupName, getUserId(), getUserName());
    }

    @GetMapping("/list")
    public List<WeChatUserRoleDO> findAll(Long groupId) {
        return weChatUsrManagerService.findRoleByGroupId(groupId);
    }

}
