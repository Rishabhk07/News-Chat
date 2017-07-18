package com.example.rishabhkhanna.peopleword.Network.interfaces;

import com.example.rishabhkhanna.peopleword.model.User;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by rishabhkhanna on 17/07/17.
 */

public interface getAuth {
    @FormUrlEncoded
    @POST("/auth")
    Call<User> userAuth(
            @Field("access_token") String access_token,
            @Field("user_id") String user_id
            );
}
