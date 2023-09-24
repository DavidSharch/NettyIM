package com.im.service.group.controller;

import com.im.service.group.model.req.*;
import com.im.service.group.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author : sharch
 * @create 2023/9/24 8:21
 */
@RestController
@RequestMapping("v1/group")
public class GroupController {
    @Autowired
    GroupService groupService;

//    @Autowired
//    GroupMessageService groupMessageService;

    /**
     * todo group crud
     * 1. 新建
     * 2. 删除
     * 3. 更新信息
     * 4. 获取用户所在的全部群
     * 5. 删除群（后台管理员和群主）
     * 6. 转移群
     * 7. 群禁言
     */
}
