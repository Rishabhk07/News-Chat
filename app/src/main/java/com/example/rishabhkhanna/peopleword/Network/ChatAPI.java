package com.example.rishabhkhanna.peopleword.Network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by rishabhkhanna on 07/08/17.
 */

public class ChatAPI {
    public static ChatAPI chatAPI = null;
    public Retrofit retrofit;

    public ChatAPI() {
        retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.0.48:9999/")
                .addConverterFactory(
                        GsonConverterFactory.create()
                ).build();
    }

    public static ChatAPI getChatInstance(){
        if(chatAPI == null){
            chatAPI = new ChatAPI();
        }
        return chatAPI;
    }
}
