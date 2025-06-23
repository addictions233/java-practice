package com.one.netty.server.message;

public class PongMessage extends Message {
    @Override
    public int getMessageType() {
        return PONG_MESSAGE;
    }
}
