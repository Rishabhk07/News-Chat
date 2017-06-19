package com.example.rishabhkhanna.peopleword.Interfaces;

import com.android.volley.VolleyError;
import com.example.rishabhkhanna.peopleword.model.ToiJson;

import java.util.ArrayList;

/**
 * Created by rishabhkhanna on 19/06/17.
 */

public interface onJsonRecieved {
    void onSuccess(ArrayList<ToiJson> fetchedNewsList);
    void onError(VolleyError error);
}
