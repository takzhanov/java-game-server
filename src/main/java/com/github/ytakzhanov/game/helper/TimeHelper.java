package com.github.ytakzhanov.game.helper;

public class TimeHelper {
    public static void sleep(int timeMS) {
        try {
            Thread.sleep(timeMS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
