package com.im.service.message.controller;

import com.im.service.message.model.SendMessageReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author : sharch
 * @create 2023/9/28 15:55
 */
@RestController
@RequestMapping("v1/message")
public class MessageController {

//    @Autowired
//    P2PMessageService p2pService;
//
//    @Autowired
//    MessageSyncService messageSyncService;

//    @RequestMapping("/send")
//    public ResponseEntity send(@RequestBody @Validated SendMessageReq req, Integer appId)  {
//        req.setAppId(appId);
//        return ResponseEntity.successResponse(p2PMessageService.send(req));
//    }
//
//    @RequestMapping("/checkSend")
//    public ResponseEntity checkSend(@RequestBody @Validated CheckSendMessageReq req)  {
//        return p2PMessageService.imServerPermissionCheck(req.getFromId(),req.getToId(),req.getAppId());
//    }
//
//    @RequestMapping("/syncOfflineMessage")
//    public ResponseEntity syncOfflineMessage(@RequestBody @Validated SyncReq req, Integer appId)  {
//        req.setAppId(appId);
//        return messageSyncService.syncOfflineMessage(req);
//    }

}
