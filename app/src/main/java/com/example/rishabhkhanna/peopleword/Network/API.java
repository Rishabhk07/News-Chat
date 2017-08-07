package com.example.rishabhkhanna.peopleword.Network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by rishabhkhanna on 17/07/17.
 */

public class API {
    private static API api = null;
    public Retrofit retrofit;

    private API() {
        retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.0.48:9890/")
                .addConverterFactory(
                        GsonConverterFactory.create()
                )
                .build();

    }

    public static API getInstance(){
        if(api == null){
            api = new API();
        }
        return api;
    }
}
