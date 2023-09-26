package com.im.tcp.handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.im.common.enums.ConnectStatusEnum;
import com.im.common.enums.Constants;
import com.im.common.enums.command.GroupEventCommand;
import com.im.common.enums.command.MessageCommand;
import com.im.common.enums.command.SystemCommand;
import com.im.common.model.UserSession;
import com.im.tcp.message.Message;
import com.im.tcp.pack.LoginPack;
import com.im.tcp.utils.RedisManager;
import com.im.tcp.utils.SessionSocketHolder;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;

import java.net.InetAddress;

/**
 * @Author : sharch
 * @create 2023/9/25 21:25
 */
@Slf4j
public class NettyServerHandler extends SimpleChannelInboundHandler<Message> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Message msg) throws Exception {
        Integer command = msg.getMessageHeader().getCommand();
        if (command == SystemCommand.LOGIN.getCommand()) {
            LoginPack loginPack = JSON.parseObject(
                    JSONObject.toJSONString(msg.getMessagePack()),
                    new TypeReference<LoginPack>() {}.getType()
            );
            String userId = loginPack.getUserId();
            this.SetChannelAttr(ctx, msg, userId);

            // 分布式session
            UserSession session = this.getUserSessionFromMsg(msg, userId);
            this.SetRedisSession(msg, session,userId);
            // 保存本地channel
            SessionSocketHolder.put(
                    msg.getMessageHeader().getAppId(), userId,
                    msg.getMessageHeader().getClientType(), msg.getMessageHeader().getImei(),
                    (NioSocketChannel) ctx.channel()
            );

            // todo 发布上线消息
        } else if (command == SystemCommand.LOGOUT.getCommand()) {
            SessionSocketHolder.removeUserSession((NioSocketChannel) ctx.channel(), false);
        } else if (command == SystemCommand.PING.getCommand()){
            ctx.channel().attr(AttributeKey.valueOf(Constants.ReadTime)).set(System.currentTimeMillis());
        }else if(command == MessageCommand.MSG_P2P.getCommand() || command == GroupEventCommand.MSG_GROUP.getCommand()){
            // todo 聊天消息处理
        } else {
            log.debug("msg:{}",msg);
        }
    }

    private void SetChannelAttr(ChannelHandlerContext ctx, Message msg, String userId) {
        String clientImei = msg.getMessageHeader().getClientType() + ":" + msg.getMessageHeader().getImei();

        ctx.channel().attr(AttributeKey.valueOf(Constants.UserId)).set(userId);
        ctx.channel().attr(AttributeKey.valueOf(Constants.ClientImei)).set(clientImei);
        ctx.channel().attr(AttributeKey.valueOf(Constants.AppId)).set(msg.getMessageHeader().getAppId());
        ctx.channel().attr(AttributeKey.valueOf(Constants.ClientType)).set(msg.getMessageHeader().getClientType());
        ctx.channel().attr(AttributeKey.valueOf(Constants.Imei)).set(msg.getMessageHeader().getImei());
    }

    private UserSession getUserSessionFromMsg(Message msg,String userId){
        UserSession userSession = new UserSession();
        userSession.setAppId(msg.getMessageHeader().getAppId());
        userSession.setClientType(msg.getMessageHeader().getClientType());
        userSession.setUserId(userId);
        userSession.setConnectState(ConnectStatusEnum.ONLINE_STATUS.getCode());
        //userSession.setBrokerId(brokerId);
        userSession.setImei(msg.getMessageHeader().getImei());
        try {
            InetAddress localHost = InetAddress.getLocalHost();
            userSession.setBrokerHost(localHost.getHostAddress());
        }catch (Exception e){
            e.printStackTrace();
        }
        return userSession;
    }

    private void SetRedisSession(Message msg, UserSession session, String userId) {
        String key = msg.getMessageHeader().getAppId() + Constants.RedisConstants.UserSessionConstants + userId;
        RedissonClient redissonClient = RedisManager.getRedissonClient();
        RMap<String, String> map = redissonClient.getMap(key);
        map.put(
                msg.getMessageHeader().getClientType() + ":" + msg.getMessageHeader().getImei(),
                JSONObject.toJSONString(session)
        );
    }
}
