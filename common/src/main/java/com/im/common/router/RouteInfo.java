package com.im.common.router;

import lombok.Data;

@Data
public final class RouteInfo {
    private String ip;
    private Integer port;

    public RouteInfo(String ip, Integer port) {
        this.ip = ip;
        this.port = port;
    }
}