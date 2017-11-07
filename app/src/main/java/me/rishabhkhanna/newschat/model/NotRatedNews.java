package me.rishabhkhanna.peopleword.model;

import java.util.ArrayList;

/**
 * Created by rishabhkhanna on 15/08/17.
 */

public class NotRatedNews {
    ArrayList<NewsJson> unRatedNews;

    public NotRatedNews(ArrayList<NewsJson> unRatedNews) {
        this.unRatedNews = unRatedNews;
    }

    public ArrayList<NewsJson> getUnRatedNews() {
        return unRatedNews;
    }

    public void setUnRatedNews(ArrayList<NewsJson> unRatedNews) {
        this.unRatedNews = unRatedNews;
    }
}
