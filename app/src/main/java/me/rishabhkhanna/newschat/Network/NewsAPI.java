package me.rishabhkhanna.newschat.Network;

import me.rishabhkhanna.newschat.Network.interfaces.getNews;

import me.rishabhkhanna.newschat.utils.Constants;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by rishabhkhanna on 21/07/17.
 */

public class NewsAPI {
    private static NewsAPI api = null;
    public me.rishabhkhanna.newschat.Network.interfaces.getNews getNews;

    private NewsAPI() {
        getNews = new Retrofit.Builder()
                .baseUrl(Constants.server_url)
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
