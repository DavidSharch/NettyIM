package com.im.common.router.base;

import com.im.common.enums.UserErrorCode;
import com.im.common.router.RouteHandle;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
public class LoopHandle implements RouteHandle {

    private AtomicLong index = new AtomicLong();

    @Override
    public String routeServer(List<String> values, String key) {
        int size = values.size();
        if(size == 0){
            UserErrorCode err = UserErrorCode.SERVER_NOT_AVAILABLE;
            log.error(
                    "一致性hash均衡错误,{},{}", err.getCode(), err.getError()
            );
        }
        Long l = index.incrementAndGet() % size;
        if(l < 0){
            l = 0L;
        }
        return values.get(l.intValue());
    }
}