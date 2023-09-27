package com.im.common.router.base;

import com.im.common.enums.UserErrorCode;
import com.im.common.router.RouteHandle;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Slf4j
public class RandomHandle implements RouteHandle {
    @Override
    public String routeServer(List<String> values, String key) {
        int size = values.size();
        if (size == 0) {
            // SERVER_NOT_AVAILABLE(71000, "没有可用的服务"),
            UserErrorCode err = UserErrorCode.SERVER_NOT_AVAILABLE;
            log.error(
                    "随机负载均衡错误,{},{}", err.getCode(), err.getError()
            );
        }
        int i = ThreadLocalRandom.current().nextInt(size);
        return values.get(i);
    }
}