package com.example.rishabhkhanna.peopleword.Network.interfaces;

import android.content.Context;

import com.example.rishabhkhanna.peopleword.model.NewsJson;
import com.example.rishabhkhanna.peopleword.utils.UrlConstants;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by rishabhkhanna on 21/07/17.
 */

public interface getNews {
    @FormUrlEncoded
    @POST(UrlConstants.BRIEFS_URL + "{page}")
    Call<ArrayList<NewsJson>> getBriefs(
            @Path("page") String page,
            @Field("auth_token") String token,
            @Field("user_id") String user_id
    );

    @FormUrlEncoded
    @POST(UrlConstants.TOP_URL + "{page}")
    Call<ArrayList<NewsJson>> getTopNews(
            @Path("page") String page,
            @Field("auth_token") String token,
            @Field("user_id") String user_id
    );

    @FormUrlEncoded
    @POST(UrlConstants.ENTERTAINMENT_URL + "{page}")
    Call<ArrayList<NewsJson>> getEntertainment(
            @Path("page") String page,
            @Field("auth_token") String token,
            @Field("user_id") String user_id
    );

    @FormUrlEncoded
    @POST(UrlConstants.INDIA_URL + "{page}")
    Call<ArrayList<NewsJson>> getIndiaNews(
            @Path("page") String page,
            @Field("auth_token") String token,
            @Field("user_id") String user_id
    );

    @FormUrlEncoded
    @POST(UrlConstants.WORLD_URL + "{page}")
    Call<ArrayList<NewsJson>> getWorldNews(
            @Path("page") String page,
            @Field("auth_token") String token,
            @Field("user_id") String user_id
    );

    @FormUrlEncoded
    @POST(UrlConstants.SPORTS_URL + "{page}")
    Call<ArrayList<NewsJson>> getSports(
            @Path("page") String page,
            @Field("auth_token") String token,
            @Field("user_id") String user_id
    );

    @FormUrlEncoded
    @POST(UrlConstants.CRICKET_URL + "{page}")
    Call<ArrayList<NewsJson>> getCricketNews(
            @Path("page") String page,
            @Field("auth_token") String token,
            @Field("user_id") String user_id
    );


    @FormUrlEncoded
    @POST(UrlConstants.BUSINESS_URL + "{page}")
    Call<ArrayList<NewsJson>> getBusinessNews(
            @Path("page") String page,
            @Field("auth_token") String token,
            @Field("user_id") String user_id
    );

    @FormUrlEncoded
    @POST(UrlConstants.EDUCATION_URL + "{page}")
    Call<ArrayList<NewsJson>> getEducatoinNews(
            @Path("page") String page,
            @Field("auth_token") String token,
            @Field("user_id") String user_id
    );

    @FormUrlEncoded
    @POST(UrlConstants.TV_URL + "{page}")
    Call<ArrayList<NewsJson>> getTVNews(
            @Path("page") String page,
            @Field("auth_token") String token,
            @Field("user_id") String user_id
    );

    @FormUrlEncoded
    @POST(UrlConstants.AUTOMOTIVE_URL + "{page}")
    Call<ArrayList<NewsJson>> getAuomotiveNews(
            @Path("page") String page,
            @Field("auth_token") String token,
            @Field("user_id") String user_id
    );

    @FormUrlEncoded
    @POST(UrlConstants.LIFESTYLE_URL + "{page}")
    Call<ArrayList<NewsJson>> getLifestyleNews(
            @Path("page") String page,
            @Field("auth_token") String token,
            @Field("user_id") String user_id
    );

    @FormUrlEncoded
    @POST(UrlConstants.ENVIRONMENT_URL + "{page}")
    Call<ArrayList<NewsJson>> getEnvironmentNews(
            @Path("page") String page,
            @Field("auth_token") String token,
            @Field("user_id") String user_id
    );

    @FormUrlEncoded
    @POST(UrlConstants.GOODGOV_URL + "{page}")
    Call<ArrayList<NewsJson>> getGoodGovNews(
            @Path("page") String page,
            @Field("auth_token") String token,
            @Field("user_id") String user_id
    );

    @FormUrlEncoded
    @POST(UrlConstants.EVENTS_URL + "{page}")
    Call<ArrayList<NewsJson>> getEvents(
            @Path("page") String page,
            @Field("auth_token") String token,
            @Field("user_id") String user_id
    );
}
