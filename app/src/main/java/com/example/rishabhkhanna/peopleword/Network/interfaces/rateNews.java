package com.example.rishabhkhanna.peopleword.Network.interfaces;

import com.example.rishabhkhanna.peopleword.model.AuthResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by rishabhkhanna on 21/07/17.
 */

public interface rateNews {
    @FormUrlEncoded
    @POST("rate/like")
    Call<AuthResponse> likeNews(
            @Field("access_token") String access_token,
            @Field("user_id") String user_id,
            @Field("news_msid") String news_msid,
            @Field("news_id") String news_id
    );

    @FormUrlEncoded
    @POST("rate/dislike")
    Call<AuthResponse> dislikeNews(
            @Field("access_token") String access_token,
            @Field("user_id") String user_id,
            @Field("news_msid") String news_msid,
            @Field("news_id") String news_id
    );
}
