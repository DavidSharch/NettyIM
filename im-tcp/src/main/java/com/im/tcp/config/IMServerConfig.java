package com.im.tcp.config;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author : sharch
 * @create 2023/9/25 20:43
 */
@Data
public class IMServerConfig {
    public TcpConfig IMServer;


    @Data
    public static class TcpConfig {
        private Integer tcpPort;
        private Integer webSocketPort;
        private Boolean enableWebSocket;
        private Integer bossThreadSize;
        private Integer workThreadSize;
        private Long heartBeatTime;

        private Integer loginModel;
        private String logicUrl;

        // 服务id，分布式环境下用来标识用户在哪个节点上
        private Integer brokerId;

        private ZkConfig zkConfig;
        private RedisConfig redis;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RedisConfig {

        /**
         * 单机模式：single 哨兵模式：sentinel 集群模式：cluster
         */
        private String mode;
        /**
         * 数据库
         */
        private Integer database;
        /**
         * 密码
         */
        private String password;
        /**
         * 超时时间
         */
        private Integer timeout;
        /**
         * 最小空闲数
         */
        private Integer poolMinIdle;
        /**
         * 连接超时时间(毫秒)
         */
        private Integer poolConnTimeout;
        /**
         * 连接池大小
         */
        private Integer poolSize;

        /**
         * redis单机配置
         */
        private RedisSingle single;

    }

    /**
     * redis单机配置
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RedisSingle {
        /**
         * 地址
         */
        private String address;
    }

    @Data
    public static class ZkConfig {
        /**
         * zk连接地址
         */
        private String zkAddr;

        /**
         * zk连接超时时间
         */
        private Integer zkConnectTimeOut;
    }
}
