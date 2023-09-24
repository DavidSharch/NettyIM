package com.im.service.user.controller;

import com.im.service.user.model.req.GetFriendOnlineStatusReq;
import com.im.service.user.model.req.GetUserOnlineStatusReq;
import com.im.service.user.model.req.SetUserCustomerStatusReq;
import com.im.service.user.model.req.SubUserOnlineStatusReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author : sharch
 * @create 2023/9/22 10:40
 */
@RestController
public class UserStatusController {

//    @Autowired
//    UserStatusService userStatusService;

    @RequestMapping("/subUserOnlineStatus")
    public ResponseEntity subscribeUserOnlineStatus(
            @RequestBody @Validated SubUserOnlineStatusReq req, String identifier
    ) {
//        req.setOperater(identifier);
//        userStatusService.subUserOnlineStatus(req);
        return ResponseEntity.ok("ok");
    }

    @RequestMapping("/setUserCustomerStatus")
    public ResponseEntity setUserCustomerStatus(
            @RequestBody @Validated SetUserCustomerStatusReq req, String identifier
    ) {
//        req.setOperater(identifier);
//        imUserStatusService.setUserCustomerStatus(req);
        return ResponseEntity.ok().body("ok");
    }

    @RequestMapping("/getFriendOnlineStatus")
    public ResponseEntity queryFriendOnlineStatus(
            @RequestBody @Validated GetFriendOnlineStatusReq req, String identifier
    ) {
//        req.setAppId(appId);
//        req.setOperater(identifier);
//        return ResponseEntity.ok(imUserStatusService.queryFriendOnlineStatus(req));
        return null;
    }

    @RequestMapping("/getUserOnlineStatus")
    public ResponseEntity queryUserOnlineStatus(
            @RequestBody @Validated GetUserOnlineStatusReq req, String identifier
    ) {
//        req.setAppId(appId);
//        req.setOperater(identifier);
//        return ResponseEntity.ok(imUserStatusService.queryUserOnlineStatus(req));
        return null;
    }
}
