package com.corejava.innerclass.innerclass1;

import javax.swing.*;

public class TalkingClassTest {
    public static void main(String[] args) {
        TalkingClock clock = new TalkingClock(1000,true);
        clock.start();
        /**
         *  一直运行,知道点击确定停止程序
         */
        JOptionPane.showMessageDialog(null,"quit program?");
        System.exit(0);  //系统正常退
    }
}
