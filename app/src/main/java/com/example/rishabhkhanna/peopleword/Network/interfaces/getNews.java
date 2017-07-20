package com.example.rishabhkhanna.peopleword.Network.interfaces;

import android.content.Context;

import com.example.rishabhkhanna.peopleword.model.NewsJson;
import com.example.rishabhkhanna.peopleword.utils.UrlConstants;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by rishabhkhanna on 21/07/17.
 */

public interface getNews {
    @GET(UrlConstants.BRIEFS_URL + "{page}")
    Call<ArrayList<NewsJson>> getBriefs(
            @Path("page") String page
    );

    @GET(UrlConstants.TOP_URL + "{page}")
    Call<ArrayList<NewsJson>> getTopNews(
            @Path("page") String page
    );

    @GET(UrlConstants.ENTERTAINMENT_URL + "{page}")
    Call<ArrayList<NewsJson>> getEntertainment(
            @Path("page") String page
    );

    @GET(UrlConstants.INDIA_URL + "{page}")
    Call<ArrayList<NewsJson>> getIndiaNews(
            @Path("page") String page
    );

    @GET(UrlConstants.WORLD_URL + "{page}")
    Call<ArrayList<NewsJson>> getWorldNews(
            @Path("page") String page
    );

    @GET(UrlConstants.SPORTS_URL + "{page}")
    Call<ArrayList<NewsJson>> getSports(
            @Path("page") String page
    );

    @GET(UrlConstants.CRICKET_URL + "{page}")
    Call<ArrayList<NewsJson>> getCricketNews(
            @Path("page") String page
    );

    @GET(UrlConstants.BUSINESS_URL + "{page}")
    Call<ArrayList<NewsJson>> getBusinessNews(
            @Path("page") String page
    );

    @GET(UrlConstants.EDUCATION_URL + "{page}")
    Call<ArrayList<NewsJson>> getEducatoinNews(
            @Path("page") String page
    );

    @GET(UrlConstants.TV_URL + "{page}")
    Call<ArrayList<NewsJson>> getTVNews(
            @Path("page") String page
    );

    @GET(UrlConstants.AUTOMOTIVE_URL + "{page}")
    Call<ArrayList<NewsJson>> getAuomotiveNews(
            @Path("page") String page
    );

    @GET(UrlConstants.LIFESTYLE_URL + "{page}")
    Call<ArrayList<NewsJson>> getLifestyleNews(
            @Path("page") String page
    );

    @GET(UrlConstants.ENVIRONMENT_URL + "{page}")
    Call<ArrayList<NewsJson>> getEnvironmentNews(
            @Path("page") String page
    );

    @GET(UrlConstants.GOODGOV_URL + "{page}")
    Call<ArrayList<NewsJson>> getGoodGovNews(
            @Path("page") String page
    );

    @GET(UrlConstants.EVENTS_URL + "{page}")
    Call<ArrayList<NewsJson>> getEvents(
            @Path("page") String page
    );
}
