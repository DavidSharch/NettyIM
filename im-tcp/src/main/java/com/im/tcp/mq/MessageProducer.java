package com.im.tcp.mq;

import com.alibaba.fastjson.JSONObject;
import com.im.common.enums.command.Command;
import com.im.common.model.UserSession;
import com.im.tcp.message.MessagePack;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author : sharch
 * @create 2023/9/28 15:09
 * 用于发送IM消息，发送一个消息，就是推送给消息队列
 */
@Service
@Slf4j
public class MessageProducer {
    @Autowired
    KafkaTemplate<String,String> kafka;

    private final String topic = "msg_demo";

    private boolean sendMessage(UserSession session, Object msg) {
        // todo 优化msg序列化方式为pb
        String content = JSONObject.toJSONString(msg);
        // 按照brokerId发送到partition上
        kafka.send(topic, session.getBrokerId().toString(), content).addCallback(success -> {
            String topic = success.getRecordMetadata().topic();
            int partition = success.getRecordMetadata().partition();
            long offset = success.getRecordMetadata().offset();
            log.info("发送成功：主题：{}，分区：{}，偏移量：{}", topic, partition, offset);
        }, failure -> log.info("发送失败：{}", failure.getMessage()));
        return true;
    }

    /**
     * 包装数据，调用sendMessage
     */
    public boolean sendPack(String toId, Command command, Object msg, UserSession session){
        MessagePack messagePack = new MessagePack();
        messagePack.setCommand(command.getCommand());
        messagePack.setToId(toId);
        messagePack.setClientType(session.getClientType());
        messagePack.setAppId(session.getAppId());
        messagePack.setImei(session.getImei());
        JSONObject jsonObject = JSONObject.parseObject(JSONObject.toJSONString(msg));
        messagePack.setData(jsonObject);

        String body = JSONObject.toJSONString(messagePack);
        return sendMessage(session, body);
    }

    /**
     * 发送给用户所在的全部设备，某个设备
     */
//    public List<ClientInfo> sendToUser(String toId,Command command,Object data,Integer appId){
//        List<UserSession> userSession = userSessionUtils.getUserSession(appId, toId);
//        List<ClientInfo> list = new ArrayList<>();
//        for (UserSession session : userSession) {
//            boolean b = sendPack(toId, command, data, session);
//            if(b){
//                list.add(new ClientInfo(session.getAppId(),session.getClientType(),session.getImei()));
//            }
//        }
//        return list;
//    }
}
