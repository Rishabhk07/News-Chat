package me.rishabhkhanna.newschat.Network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by rishabhkhanna on 08/08/17.
 */

public class GetProfilePicture {
    public static GetProfilePicture getDP = null;
    public Retrofit retrofit;

    public GetProfilePicture() {
        retrofit = new Retrofit.Builder()
                .baseUrl("http://graph.facebook.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static GetProfilePicture getInstance() {
        if (getDP == null) {
            getDP = new GetProfilePicture();
        }
        return getDP;
    }
}