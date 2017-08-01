package com.example.rishabhkhanna.peopleword.Network.interfaces;

import com.example.rishabhkhanna.peopleword.model.AuthResponse;
import com.example.rishabhkhanna.peopleword.model.Topic;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by rishabhkhanna on 17/07/17.
 */

public interface getAuth {
    @FormUrlEncoded
    @POST("auth/facebook")
    Call<AuthResponse> facebookUserAuth(
            @Field("access_token") String access_token,
            @Field("user_id") String user_id,
            @Field("firebase_token") String firebase_token
            );
    @FormUrlEncoded
    @POST("auth/fbUpdateAcessToken")
    Call<AuthResponse> facebookTokenUpdate(
            @Field("access_token") String access_token,
            @Field("user_id") String user_id
    );

    @FormUrlEncoded
    @POST("auth/updateFcmToken")
    Call<AuthResponse> fcmTokenUpdate(
            @Field("token") String fcmToken,
            @Field("user_id") String userId
    );

    @FormUrlEncoded
    @POST("auth/updateTopics")
    Call<AuthResponse> updateUserTopics(
            @Field("user_topics") String user_topics,
            @Field("user_id") String user_id
            );

}
