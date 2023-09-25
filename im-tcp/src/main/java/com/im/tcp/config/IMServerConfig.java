package com.im.tcp.config;

import lombok.Data;

/**
 * @Author : sharch
 * @create 2023/9/25 20:43
 */
@Data
public class IMServerConfig {
    public TcpConfig IMServer;

    @Data
    public static class TcpConfig {
        private Integer tcpPort;// tcp 绑定的端口号
        private Integer webSocketPort; // webSocket 绑定的端口号
        private Integer bossThreadSize; // boss线程 默认=1
        private Integer workThreadSize; //work线程
        private Long heartBeatTime; //心跳超时时间 单位毫秒
        private Integer loginModel;
    }
}
