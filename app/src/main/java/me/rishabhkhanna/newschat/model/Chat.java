package me.rishabhkhanna.newschat.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by rishabhkhanna on 04/08/17.
 */

public class Chat {
    String message;
    String msid;
    Long news_id;
    @SerializedName("from")
    String user_id;

    @SerializedName("anonym")
    Boolean anonym_user;

    public Chat(String message, String msid, Long news_id, String user_id, Boolean anonym_user) {
        this.message = message;
        this.msid = msid;
        this.news_id = news_id;
        this.user_id = user_id;
        this.anonym_user = anonym_user;
    }

    public Boolean getAnonym_user() {
        return anonym_user;
    }

    public void setAnonym_user(Boolean anonym_user) {
        this.anonym_user = anonym_user;
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
