package me.rishabhkhanna.peopleword.Network.interfaces;

import me.rishabhkhanna.peopleword.model.NewsJson;
import me.rishabhkhanna.peopleword.utils.UrlConstants;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by rishabhkhanna on 12/09/17.
 */

public interface getNotificationNews {

    @GET(UrlConstants.NOTIFY + "/{msid}/{news_id}")
    Call<NewsJson> getThisNews(
            @Path("msid") String table_key,
            @Path("news_id") String news_id
    );
}
