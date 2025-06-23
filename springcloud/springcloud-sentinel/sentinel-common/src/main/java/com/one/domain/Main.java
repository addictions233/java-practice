package com.one.domain;

/**
 * @author one
 * @description TODO
 * @date 2024-9-25
 */
public class Main {

    public static void main(String[] args) {
        CloudVideo cloudVideo = new CloudVideo();
        cloudVideo.setServeInfo("123");
        String serveInfo = cloudVideo.getServeInfo();

        CloudVideoOrder cloudVideoOrder = new CloudVideoOrder();
        cloudVideoOrder.setVideoId(123);
        Integer state = cloudVideoOrder.getState();
    }
}
