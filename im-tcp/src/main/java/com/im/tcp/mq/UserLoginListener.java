package com.im.tcp.mq;

import com.alibaba.fastjson.JSONObject;
import com.im.common.ClientType;
import com.im.common.enums.Constants;
import com.im.common.enums.DeviceMultiLoginEnum;
import com.im.common.enums.command.SystemCommand;
import com.im.tcp.message.MessagePack;
import com.im.tcp.model.UserClientDto;
import com.im.tcp.utils.SessionSocketHolder;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;

import java.util.List;

@Configuration
@Slf4j
public class UserLoginListener {

    @Value("${loginModel}")
    private Integer loginModel;

    @KafkaListener(topics = "user_login")
    public void consume(String message) {
        log.info("收到用户上线:{}", message);
        UserClientDto dto = JSONObject.parseObject(message, UserClientDto.class);
        List<NioSocketChannel> nioSocketChannels = SessionSocketHolder.get(dto.getAppId(), dto.getUserId());

        for (NioSocketChannel nioSocketChannel : nioSocketChannels) {
            String imei = (String) nioSocketChannel.attr(AttributeKey.valueOf(Constants.Imei)).get();
            Integer clientType = (Integer) nioSocketChannel.attr(AttributeKey.valueOf(Constants.ClientType)).get();
            if (loginModel == DeviceMultiLoginEnum.ONE.getLoginMode()) {
                // 只允许一台设备登录
                if (!(clientType + ":" + imei).equals(dto.getClientType() + ":" + dto.getImei())) {
                    this.sendOfflienMsg(nioSocketChannel);
                }
            } else if (loginModel == DeviceMultiLoginEnum.TWO.getLoginMode()) {
                if (dto.getClientType() == ClientType.WEB.getCode() || clientType == ClientType.WEB.getCode()) {
                    continue;
                }
                if (!(clientType + ":" + imei).equals(dto.getClientType() + ":" + dto.getImei())) {
                    this.sendOfflienMsg(nioSocketChannel);
                }
            } else if (loginModel == DeviceMultiLoginEnum.THREE.getLoginMode()) {
                if (dto.getClientType() == ClientType.WEB.getCode()) {
                    continue;
                }
                boolean isSameClient = false;
                if ((clientType == ClientType.IOS.getCode() || clientType == ClientType.ANDROID.getCode()) && (dto.getClientType() == ClientType.IOS.getCode() || dto.getClientType() == ClientType.ANDROID.getCode())) {
                    isSameClient = true;
                }
                if ((clientType == ClientType.MAC.getCode() || clientType == ClientType.WINDOWS.getCode()) && (dto.getClientType() == ClientType.MAC.getCode() || dto.getClientType() == ClientType.WINDOWS.getCode())) {
                    isSameClient = true;
                }
                if (isSameClient && !(clientType + ":" + imei).equals(dto.getClientType() + ":" + dto.getImei())) {
                    this.sendOfflienMsg(nioSocketChannel);
                }
            }
        }
    }

    /**
     * 发送下线消息
     * @param channel
     */
    private void sendOfflienMsg(NioSocketChannel channel) {
        MessagePack<Object> pack = new MessagePack<>();
        pack.setToId((String) channel.attr(AttributeKey.valueOf(Constants.UserId)).get());
        pack.setUserId((String) channel.attr(AttributeKey.valueOf(Constants.UserId)).get());
        pack.setCommand(SystemCommand.MUTUALLOGIN.getCommand());
        channel.writeAndFlush(pack);
    }

}