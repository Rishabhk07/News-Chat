package me.rishabhkhanna.peopleword.Interfaces;

import com.android.volley.VolleyError;
import me.rishabhkhanna.peopleword.model.NewsJson;

import java.util.ArrayList;

/**
 * Created by rishabhkhanna on 19/06/17.
 */

public interface onJsonRecieved {
    void onSuccess(ArrayList<NewsJson> fetchedNewsList);
    void onError(VolleyError error);
}
