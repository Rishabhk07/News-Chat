package me.rishabhkhanna.newschat.model;

import io.realm.RealmObject;

/**
 * Created by rishabhkhanna on 22/07/17.
 */

public class Topic extends RealmObject {
    String name;
    String key;
    boolean value;
    int position;


    public Topic(String name, String key, int position) {
        this.name = name;
        this.key = key;
        this.position = position;
    }

    public Topic(String name, String key, Boolean value) {
        this.name = name;
        this.key = key;
        this.value = value;
    }

    public Topic() {
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getKey() {

        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}