package com.example.rishabhkhanna.peopleword.Network.interfaces;

import com.example.rishabhkhanna.peopleword.model.AuthResponse;
import com.example.rishabhkhanna.peopleword.model.NewsJson;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

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

    @FormUrlEncoded
    @POST("rate/getNews")
    Call<ArrayList<NewsJson>> getNews(
            @Field("user_id") String userId,
            @Query("msid") ArrayList<String> user_id
    );
}