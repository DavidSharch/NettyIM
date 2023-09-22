package com.im.service.user.controller;

import com.im.common.ClientType;
import com.im.service.user.model.req.*;
import com.im.service.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.http.conn.routing.RouteInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author : sharch
 * @create 2023/9/21 18:17
 */
@RestController
@RequestMapping("v1/user")
@Api(value = "用户接口", tags = {"用户接口"})
public class UserController {
    @Autowired
    UserService service;

    /**
     * 新增用户，支持批量新增
     */
    @PostMapping("importUser")
    @ApiOperation("新增用户")
    public ResponseEntity importUser(@RequestBody ImportUserReq req) {
        return service.importUser(req);
    }

    @PostMapping("/deleteUser")
    @ApiOperation("删除用户")
    public ResponseEntity deleteUser(@RequestBody @Validated DeleteUserReq req) {
        return service.deleteUser(req);
    }


    @RequestMapping("/getUserInfo")
    public ResponseEntity getUserInfo(@RequestBody GetUserInfoReq req, Integer appId) {//@Validated
        req.setAppId(appId);
        return service.getUserInfo(req);
    }

    @RequestMapping("/modifyUserInfo")
    public ResponseEntity modifyUserInfo(@RequestBody @Validated ModifyUserInfoReq req) {
        return service.modifyUserInfo(req);
    }
}
