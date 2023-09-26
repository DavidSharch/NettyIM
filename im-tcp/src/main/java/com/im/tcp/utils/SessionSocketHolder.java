package com.im.tcp.utils;

import com.alibaba.fastjson.JSONObject;
import com.im.common.enums.ConnectStatusEnum;
import com.im.common.enums.Constants;
import com.im.common.enums.command.UserEventCommand;
import com.im.common.model.UserSession;
import com.im.tcp.message.MessageHeader;
import com.im.tcp.model.UserClientDto;
import com.sun.org.apache.xpath.internal.operations.Bool;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
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

    public static NioSocketChannel get(Integer appId,String userId,
                                       Integer clientType,String imei){
        UserClientDto dto = new UserClientDto();
        dto.setImei(imei);
        dto.setAppId(appId);
        dto.setClientType(clientType);
        dto.setUserId(userId);
        return channelsMap.get(dto);
    }

    public static List<NioSocketChannel> get(Integer appId , String id) {

        Set<UserClientDto> channelInfos = channelsMap.keySet();
        List<NioSocketChannel> channels = new ArrayList<>();

        channelInfos.forEach(channel ->{
            if(channel.getAppId().equals(appId) && id.equals(channel.getUserId())){
                channels.add(channelsMap.get(channel));
            }
        });

        return channels;
    }

    public static void remove(Integer appId,String userId,Integer clientType,String imei){
        UserClientDto dto = new UserClientDto();
        dto.setAppId(appId);
        dto.setImei(imei);
        dto.setClientType(clientType);
        dto.setUserId(userId);
        channelsMap.remove(dto);
    }

    public static void remove(NioSocketChannel channel) {
        channelsMap.entrySet().stream().filter(entity -> entity.getValue() == channel)
                .forEach(entry -> channelsMap.remove(entry.getKey()));
    }

    /**
     * 删除用户session，同时删除map和redis中的数据，然后下线（关闭netty连接）
     */
    public static void removeUserSession(NioSocketChannel nioSocketChannel, Boolean isOffline) {
        removeLocalSession(nioSocketChannel);
        removeRedisSession(nioSocketChannel, isOffline);
        sendOfflineMsg(nioSocketChannel);
        nioSocketChannel.close();
    }

    private static void sendOfflineMsg(NioSocketChannel ch){
        String userId = (String) ch.attr(AttributeKey.valueOf(Constants.UserId)).get();
        Integer appId = (Integer) ch.attr(AttributeKey.valueOf(Constants.AppId)).get();
        Integer clientType = (Integer) ch.attr(AttributeKey.valueOf(Constants.ClientType)).get();
        String imei = (String) ch.attr(AttributeKey.valueOf(Constants.Imei)).get();

        MessageHeader messageHeader = new MessageHeader();
        messageHeader.setAppId(appId);
        messageHeader.setImei(imei);
        messageHeader.setClientType(clientType);
//        UserStatusChangeNotifyPack userStatusChangeNotifyPack = new UserStatusChangeNotifyPack();
//        userStatusChangeNotifyPack.setAppId(appId);
//        userStatusChangeNotifyPack.setUserId(userId);
//        userStatusChangeNotifyPack.setStatus(ConnectStatusEnum.OFFLINE_STATUS.getCode());
//        MqMessageProducer.sendMessage(userStatusChangeNotifyPack,messageHeader, UserEventCommand.USER_ONLINE_STATUS_CHANGE.getCommand());
    }

    private static void removeLocalSession(NioSocketChannel ch){
        String userId = (String) ch.attr(AttributeKey.valueOf(Constants.UserId)).get();
        Integer appId = (Integer) ch.attr(AttributeKey.valueOf(Constants.AppId)).get();
        Integer clientType = (Integer) ch.attr(AttributeKey.valueOf(Constants.ClientType)).get();
        String imei = (String) ch.attr(AttributeKey.valueOf(Constants.Imei)).get();
        SessionSocketHolder.remove(appId, userId, clientType, imei);
    }

    private static void removeRedisSession(NioSocketChannel ch, Boolean isOffline) {
        String userId = (String) ch.attr(AttributeKey.valueOf(Constants.UserId)).get();
        Integer appId = (Integer) ch.attr(AttributeKey.valueOf(Constants.AppId)).get();
        Integer clientType = (Integer) ch.attr(AttributeKey.valueOf(Constants.ClientType)).get();
        String imei = (String) ch.attr(AttributeKey.valueOf(Constants.Imei)).get();

        RedissonClient redissonClient = RedisManager.getRedissonClient();
        RMap<Object, Object> map = redissonClient.getMap(appId + Constants.RedisConstants.UserSessionConstants + userId);
        if(isOffline){
            // 更新为离线状态
            String sessionStr = map.get(clientType.toString() + ":" + imei).toString();
            if (!StringUtils.isEmpty(sessionStr)) {
                UserSession userSession = JSONObject.parseObject(sessionStr, UserSession.class);
                userSession.setConnectState(ConnectStatusEnum.OFFLINE_STATUS.getCode());
                map.put(clientType + ":" + imei, JSONObject.toJSONString(userSession));
            }
        } else {
            map.remove(clientType + ":" + imei);
        }
    }
}
