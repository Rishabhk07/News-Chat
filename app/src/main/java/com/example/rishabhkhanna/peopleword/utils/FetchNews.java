package com.example.rishabhkhanna.peopleword.utils;

import android.content.Context;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.rishabhkhanna.peopleword.Adapters.Adapters;
import com.example.rishabhkhanna.peopleword.MainActivity;
import com.example.rishabhkhanna.peopleword.model.ToiJson;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

import static com.android.volley.Request.Method.GET;

/**
 * Created by rishabhkhanna on 26/12/16.
 */

public class FetchNews {
    static RequestQueue queue;
    public static final String TAG = "Fetch news Class";

    public static void getNewsJson(final Context context){
        queue = Volley.newRequestQueue(context);

        String url = "http://timesofindia.indiatimes.com" +
                "/feeds/newslistingfeed/tag-alrt,msid-48986328,feedtype-sjson,type-brief.cms?andver=" +
                "417&platform=android&adreqfrm=sec";

        StringRequest stringRequest = new StringRequest(GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

//                Log.d(TAG , response);


                JSONObject jsonObject = null;
                JSONArray itemObject = null;
                String oneObject = null;
                try {
                    jsonObject = new JSONObject(response);
                    itemObject = jsonObject.getJSONArray("items");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Gson gson = new Gson();
                ToiJson[] toiJson = gson.fromJson(itemObject.toString(), ToiJson[].class );
                Log.d(TAG, toiJson[0].getHl());
                Log.d(TAG, toiJson[0].getSyn());
                Log.d(TAG, toiJson[0].getImageid());

                ArrayList<ToiJson> newsList = new ArrayList<ToiJson>(Arrays.asList(toiJson));
                Log.d(TAG, newsList.get(0).getHl().toString());
//                MainActivity.newsList.addAll(newsList);
//                MainActivity.swipeCardAdapter.notifyDataSetChanged();
                Adapters.SwipeCardAdapter adapter = Adapters.getSwipeCardAdapter(newsList , context);
                MainActivity.swipeDeck.setAdapter(adapter);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        queue.add(stringRequest);

    }


}
