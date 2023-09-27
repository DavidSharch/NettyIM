package com.im.service.user.controller;

import com.im.common.BaseErrorCode;
import com.im.common.ClientType;
import com.im.common.router.RouteHandle;
import com.im.common.router.RouteInfo;
import com.im.common.router.base.RandomHandle;
import com.im.common.router.hash.ConsistentHashHandle;
import com.im.service.user.model.req.LoginReq;
import com.im.service.user.service.UserService;
import com.im.service.utils.ZKit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author : sharch
 * @create 2023/9/27 20:38
 */
@Slf4j
@RestController
public class UserLoginController {

    @Autowired
    UserService service;

    @Autowired
    ZKit zKit;

    /**
     * 见beanconfig
     */
    @Autowired
    RouteHandle routeHandle;

    @RequestMapping("/login")
    public ResponseEntity login(@RequestBody @Validated LoginReq req, Integer appId) {
        req.setAppId(appId);
        if (service.login(req)) {
            List<String> allNode;
            if (req.getClientType() == ClientType.WEB.getCode()) {
                allNode = zKit.getAllWebNode();
            } else {
                allNode = zKit.getAllTcpNode();
            }
            String s = routeHandle.routeServer(allNode, req.getUserId());
            RouteInfo parse = this.parse(s);
            return ResponseEntity.ok(parse);
        }

        return ResponseEntity.badRequest().body("请求失败");
    }

    private RouteInfo parse(String info) {
        try {
            String[] serverInfo = info.split(":");
            return new RouteInfo(serverInfo[0], Integer.parseInt(serverInfo[1]));
        } catch (Exception e) {
            BaseErrorCode err = BaseErrorCode.PARAMETER_ERROR;
            log.error(
                    "{},{}", err.getCode(), err.getError()
            );
        }
        return null;
    }
}
