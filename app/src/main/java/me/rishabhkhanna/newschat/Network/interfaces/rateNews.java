package me.rishabhkhanna.newschat.Network.interfaces;

import me.rishabhkhanna.newschat.model.NewsJson;
import me.rishabhkhanna.newschat.model.RatedNewsPojo;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by rishabhkhanna on 21/07/17.
 */

public interface rateNews {
    @FormUrlEncoded
    @POST("rate/like")
    Call<NewsJson> likeNews(
            @Field("access_token") String access_token,
            @Field("user_id") String user_id,
            @Field("news_msid") String news_msid,
            @Field("news_id") String news_id,
            @Field("rating") long rating
    );

    @FormUrlEncoded
    @POST("rate/dislike")
    Call<NewsJson> dislikeNews(
            @Field("access_token") String access_token,
            @Field("user_id") String user_id,
            @Field("news_msid") String news_msid,
            @Field("news_id") String news_id,
            @Field("rating") long rating
    );

    @FormUrlEncoded
    @POST("rate/getNews")
    Call<ArrayList<NewsJson>> getNews(
            @Field("user_id") String userId,
            @Query("msid") ArrayList<String> user_id
    );

    @FormUrlEncoded
    @POST ("rate/getRatedNews")
    Call<RatedNewsPojo> getRatedNews(
            @Field("user_id") String userId,
            @Field("user_rating") int user_rating
    );

    @FormUrlEncoded
    @POST("rate/getNews")
    Call<ArrayList<ArrayList<NewsJson>>> getNews(
            @Field("user_id") String userId,
            @Query("offset") int offset
    );

}
