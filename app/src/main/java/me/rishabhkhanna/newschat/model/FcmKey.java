package me.rishabhkhanna.newschat.model;

/**
 * Created by rishabhkhanna on 16/10/17.
 */

public class FcmKey {
    String fcmKey;

    public FcmKey(String fcmKey) {
        this.fcmKey = fcmKey;
    }

    public String getFcmKey() {
        return fcmKey;
    }

    public void setFcmKey(String fcmKey) {
        this.fcmKey = fcmKey;
    }
}
