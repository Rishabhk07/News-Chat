package com.example.rishabhkhanna.peopleword.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by rishabhkhanna on 22/07/17.
 */

public class Topic extends RealmObject {
    String name;
    String key;
    int position;

    public Topic(String name, String key, int position) {
        this.name = name;
        this.key = key;
        this.position = position;
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
