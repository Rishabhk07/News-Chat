package com.example.rishabhkhanna.peopleword.model;

/**
 * Created by rishabhkhanna on 05/08/17.
 */

public class ChatRoom {
    String msid;
    String user_id;
    Long news_id;

    public ChatRoom(String msid, String user_id, Long news_id) {
        this.msid = msid;
        this.user_id = user_id;
        this.news_id = news_id;
    }

    public String getMsid() {
        return msid;
    }

    public void setMsid(String msid) {
        this.msid = msid;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public Long getNews_id() {
        return news_id;
    }

    public void setNews_id(Long news_id) {
        this.news_id = news_id;
    }
}

