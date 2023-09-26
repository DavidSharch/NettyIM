package com.im.tcp.handler;

import com.im.common.enums.Constants;
import com.im.tcp.utils.SessionSocketHolder;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.AttributeKey;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author : sharch
 * @create 2023/9/26 15:40
 */
@Slf4j
public class HeartBeatHandler extends ChannelInboundHandlerAdapter {
    private Long heartBeatTime;

    public HeartBeatHandler(Long heartBeatTime) {
        this.heartBeatTime = heartBeatTime;
    }

    /**
     * 重写心跳超时触发事件
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        // 判断evt是否是IdleStateEvent（用于触发用户事件，包含 读空闲/写空闲/读写空闲 ）
        if (!(evt instanceof IdleStateEvent)) {
            return;
        }
        IdleStateEvent event = (IdleStateEvent) evt;
        String userId = ctx.channel().attr(AttributeKey.valueOf(Constants.UserId)).get().toString();
        if (event.state() == IdleState.READER_IDLE) {
            log.debug("userId-{},读空闲", userId);
        } else if (event.state() == IdleState.WRITER_IDLE) {
            log.debug("userId-{},写空闲", userId);
        } else if (event.state() == IdleState.ALL_IDLE) {
            // 读写都超时，下线
            Long lastReadTime = (Long) ctx.channel().attr(AttributeKey.valueOf(Constants.ReadTime)).get();
            long now = System.currentTimeMillis();
            if (lastReadTime != null && now - lastReadTime > heartBeatTime) {
                SessionSocketHolder.removeUserSession((NioSocketChannel) ctx.channel(), true);
            }
        }
    }
}
