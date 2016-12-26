package com.example.rishabhkhanna.peopleword.utils;

import android.content.Context;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.rishabhkhanna.peopleword.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.android.volley.Request.Method.GET;

/**
 * Created by rishabhkhanna on 26/12/16.
 */

public class FetchNews {
    static RequestQueue queue;
    public static final String TAG = "Fetch news Class";

    public static void getNewsJson(Context context){
        queue = Volley.newRequestQueue(context);

        String url = "http://timesofindia.indiatimes.com" +
                "/feeds/newslistingfeed/tag-alrt,msid-48986328,feedtype-sjson,type-brief.cms?andver=" +
                "417&platform=android&adreqfrm=sec";

        StringRequest stringRequest = new StringRequest(GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d(TAG , response);


                JSONObject jsonObject = null;
                JSONArray itemObject = null;
                try {
                    jsonObject = new JSONObject(response);
                    itemObject = jsonObject.getJSONArray("items");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Log.d(TAG , itemObject.toString());




            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        queue.add(stringRequest);

    }


}
