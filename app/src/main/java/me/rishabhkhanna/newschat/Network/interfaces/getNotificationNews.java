package me.rishabhkhanna.newschat.Network.interfaces;

import me.rishabhkhanna.newschat.model.NewsJson;
import me.rishabhkhanna.newschat.utils.UrlConstants;
import retrofit2.Call;
import retrofit2.http.GET;
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
