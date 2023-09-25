package com.im.tcp.message;

import lombok.Data;

@Data
public class Message {

    private MessageHeader messageHeader;

    private Object messagePack;

}