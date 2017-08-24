package me.rishabhkhanna.peopleword.Network.interfaces;

import me.rishabhkhanna.peopleword.model.Chat;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by rishabhkhanna on 07/08/17.
 */

public interface getChats {
    @FormUrlEncoded
    @POST("/getChats")
    Call<ArrayList<Chat>> getChat(
            @Field("news_id") Long news_id,
            @Field("msid") String msid
    );

}
