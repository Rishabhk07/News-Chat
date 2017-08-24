package me.rishabhkhanna.peopleword.Network;

import me.rishabhkhanna.peopleword.Network.interfaces.getNews;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by rishabhkhanna on 21/07/17.
 */

public class NewsAPI {
    private static NewsAPI api = null;
    public me.rishabhkhanna.peopleword.Network.interfaces.getNews getNews;

    private NewsAPI() {
        getNews = new Retrofit.Builder()
                .baseUrl("http://192.168.1.39:9090/")
                .addConverterFactory(
                        GsonConverterFactory.create()
                )
                .build()
                .create(getNews.class);

    }

    public static NewsAPI getInstance(){
        if(api == null){
            api = new NewsAPI();
        }
        return api;
    }
}
