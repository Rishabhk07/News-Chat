package com.example.rishabhkhanna.peopleword.model;

/**
 * Created by rishabhkhanna on 22/07/17.
 */

public class Topic {
    String name;
    String key;

    public Topic(String name, String key) {
        this.name = name;
        this.key = key;
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
