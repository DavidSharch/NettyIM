package com.im.service.user.controller;

import com.im.service.user.model.req.DeleteUserReq;
import com.im.service.user.model.req.ImportUserReq;
import com.im.service.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

//    /**
//     * @description im的登录接口，返回im地址
//     */
//    @RequestMapping("/login")
//    public ResponseEntity login(@RequestBody @Validated LoginReq req) {
//        req.setAppId(appId);
//        ResponseEntity login = service.login(req);
//        if (login.getStatusCode() == HttpStatusCode.valueOf(200)) {
//            List<String> allNode;
//            if (req.getClientType() == ClientType.WEB.getCode()) {
//                allNode = zKit.getAllWebNode();
//            } else {
//                allNode = zKit.getAllTcpNode();
//            }
//            String s = routeHandle.routeServer(allNode, req.getUserId());
//            RouteInfo parse = RouteInfoParseUtil.parse(s);
//            return ResponseEntity.ok(parse);
//        }
//        return ResponseEntity.internalServerError().body("");
//    }

//    @RequestMapping("/getUserSequence")
//    public ResponseEntity getUserSequence(
//            @RequestBody @Validated GetUserSequenceReq req
//    ) {
//        req.setAppId(appId);
//        return service.getUserSequence(req);
//    }

//    @RequestMapping("/subscribeUserOnlineStatus")
//    public ResponseEntity subscribeUserOnlineStatus(
//            @RequestBody @Validated SubscribeUserOnlineStatusReq req,String identifier
//    ) {
//        req.setAppId(appId);
//        req.setOperater(identifier);
//        imUserStatusService.subscribeUserOnlineStatus(req);
//        return ResponseEntity.ok("ok");
//    }

//    @RequestMapping("/setUserCustomerStatus")
//    public ResponseEntity setUserCustomerStatus(
//            @RequestBody @Validated SetUserCustomerStatusReq req,String identifier
//    ) {
//        req.setAppId(appId);
//        req.setOperater(identifier);
//        imUserStatusService.setUserCustomerStatus(req);
//        return ResponseEntity.ok().body("ok");
//    }

//    @RequestMapping("/queryFriendOnlineStatus")
//    public ResponseEntity queryFriendOnlineStatus(
//            @RequestBody @Validated PullFriendOnlineStatusReq req,String identifier
//    ) {
//        req.setAppId(appId);
//        req.setOperater(identifier);
//        return ResponseEntity.ok(imUserStatusService.queryFriendOnlineStatus(req));
//    }

//    @RequestMapping("/queryUserOnlineStatus")
//    public ResponseEntity queryUserOnlineStatus(
//            @RequestBody @Validated PullUserOnlineStatusReq req,String identifier
//    ) {
//        req.setAppId(appId);
//        req.setOperater(identifier);
//        return ResponseEntity.ok(imUserStatusService.queryUserOnlineStatus(req));
//    }
}
