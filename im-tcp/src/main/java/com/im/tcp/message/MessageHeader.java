package com.im.tcp.message;

import lombok.Data;

@Data
public class MessageHeader {

    //消息操作指令 十六进制 一个消息的开始通常以0x开头
    //4字节
    private Integer command;

    //4字节 版本号
    private Integer version;

    //4字节 端类型
    private Integer clientType;

    // 4字节 appId
    private Integer appId;

    //4字节 解析类型 0x0:Json,0x1:ProtoBuf,0x2:Xml,默认:0x0
    private Integer messageType = 0x0; // 16进制

    //4字节 imel长度
    private Integer imeiLength;

    //4字节 包体长度
    private int length;

    //imei号
    private String imei;
}