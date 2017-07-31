package com.example.rishabhkhanna.peopleword.Network.interfaces;

import com.example.rishabhkhanna.peopleword.model.AuthResponse;

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
            @Field("fcm_token") String fcm_token
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
}
