package com.im.tcp.utils;

import com.im.tcp.config.IMServerConfig;
import io.netty.bootstrap.BootstrapConfig;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.client.codec.StringCodec;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.springframework.util.StringUtils;

public class RedisManager {

    private static RedissonClient redissonClient;

    private static Integer loginModel;

    public static void init(IMServerConfig config){
        loginModel = config.getIMServer().getLoginModel();
        redissonClient = getRedissonClient(config.getIMServer().getRedis());
//        UserLoginMessageListener userLoginMessageListener = new UserLoginMessageListener(loginModel);
//        userLoginMessageListener.listenerUserLogin();
    }

    public static RedissonClient getRedissonClient(){
        return redissonClient;
    }

    public static RedissonClient getRedissonClient(IMServerConfig.RedisConfig redisConfig) {
        Config config = new Config();
        String node = redisConfig.getSingle().getAddress();
        node = node.startsWith("redis://") ? node : "redis://" + node;
        // redis自带的单例模式
        SingleServerConfig serverConfig = config.useSingleServer()
                .setAddress(node)
                .setDatabase(redisConfig.getDatabase())
                .setTimeout(redisConfig.getTimeout())
                .setConnectionMinimumIdleSize(redisConfig.getPoolMinIdle())
                .setConnectTimeout(redisConfig.getPoolConnTimeout())
                .setConnectionPoolSize(redisConfig.getPoolSize());
        if (!StringUtils.isEmpty(redisConfig.getPassword())) {
            serverConfig.setPassword(redisConfig.getPassword());
        }
        StringCodec stringCodec = new StringCodec();
        config.setCodec(stringCodec);
        return Redisson.create(config);
    }

}