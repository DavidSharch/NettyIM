package com.im.tcp.utils;

import com.im.tcp.model.UserClientDto;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author : sharch
 * @create 2023/9/26 9:44
 * 保存用户登录的channel
 */
public class SessionSocketHolder {
    private static final Map<UserClientDto, NioSocketChannel> channelsMap = new ConcurrentHashMap<>();

    public static void put(
            Integer appId, String userId, Integer clientType, String imei, NioSocketChannel channel
    ) {
        UserClientDto dto = new UserClientDto();
        dto.setImei(imei);
        dto.setAppId(appId);
        dto.setClientType(clientType);
        dto.setUserId(userId);
        channelsMap.put(dto, channel);
    }

    public static NioSocketChannel GetChannelByUserId(String userId) {
        return channelsMap.getOrDefault(userId, null);
    }
}
