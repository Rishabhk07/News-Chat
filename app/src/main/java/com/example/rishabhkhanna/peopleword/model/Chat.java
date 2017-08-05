package com.example.rishabhkhanna.peopleword.model;

/**
 * Created by rishabhkhanna on 04/08/17.
 */

public class Chat {
    String message;
    String msid;
    Long news_id;
    String user_id;

    public Chat(String message, String msid, Long news_id, String user_id) {
        this.message = message;
        this.msid = msid;
        this.news_id = news_id;
        this.user_id = user_id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMsid() {
        return msid;
    }

    public void setMsid(String msid) {
        this.msid = msid;
    }


    public Long getNews_id() {
        return news_id;
    }

    public void setNews_id(Long news_id) {
        this.news_id = news_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
