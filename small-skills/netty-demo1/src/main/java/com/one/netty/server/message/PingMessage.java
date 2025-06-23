package com.one.netty.server.message;

public class PingMessage extends Message {
    @Override
    public int getMessageType() {
        return PING_MESSAGE;
    }
}
