package me.rishabhkhanna.peopleword.utils;

import android.content.Context;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import me.rishabhkhanna.peopleword.Interfaces.onJsonRecieved;
import me.rishabhkhanna.peopleword.model.NewsJson;

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
    public static final String TAG = "FetchNews";

    public static void getNewsJson(final Context context, final onJsonRecieved onJsonRecieved){
        queue = Volley.newRequestQueue(context);

        String url = "http://timesofindia.indiatimes.com" +
                "/feeds/newslistingfeed/tag-alrt,msid-48986328,feedtype-sjson,type-brief.cms?andver=" +
                "417&platform=android&adreqfrm=sec";

        StringRequest stringRequest = new StringRequest(GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                JSONObject jsonObject = null;
                JSONArray itemObject = null;
                String oneObject = null;
                try {
                    jsonObject = new JSONObject(response);
                    itemObject = jsonObject.getJSONArray("items");
                } catch (JSONException e) {
                    Log.d(TAG, "onResponse: ");
                    e.printStackTrace();
                }

                Gson gson = new Gson();
                NewsJson[] toiJson = gson.fromJson(itemObject.toString(), NewsJson[].class );
                Log.d(TAG, toiJson[0].getHl());
                Log.d(TAG, toiJson[0].getSyn());
                Log.d(TAG, toiJson[0].getImageid());

                ArrayList<NewsJson> newsList = new ArrayList<NewsJson>(Arrays.asList(toiJson));
                Log.d(TAG, newsList.get(0).getHl().toString());
                onJsonRecieved.onSuccess(newsList);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "onErrorResponse: Error in Fetch News");
                onJsonRecieved.onError(error);
            }
        });

        queue.add(stringRequest);

    }


    public static void getUrlNews(final Context context, final onJsonRecieved onJsonRecieved,String url){
        queue = Volley.newRequestQueue(context);


        StringRequest stringRequest = new StringRequest(GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

//
//                JSONObject jsonObject = null;
//                JSONArray itemObject = null;
//                String oneObject = null;
//                try {
//                    jsonObject = new JSONObject(response);
//                    itemObject = jsonObject.getJSONArray("items");
//                } catch (JSONException e) {
//                    Log.d(TAG, "onResponse: ");
//                    e.printStackTrace();
//                }


                Log.d(TAG, "onResponse: REsponse : " + response);
                if(!response.equals("[]")){
                    Gson gson = new Gson();
                    NewsJson[] toiJson = gson.fromJson(response, NewsJson[].class);

//                Log.d(TAG, String.valueOf(toiJson[0].getSyn()));
//                Log.d(TAG, toiJson[0].getImageid());

                    ArrayList<NewsJson> newsList = new ArrayList<NewsJson>(Arrays.asList(toiJson));
                    Log.d(TAG, newsList.get(0).getHl().toString());
                    onJsonRecieved.onSuccess(newsList);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "onErrorResponse: Error in Fetch News");
                onJsonRecieved.onError(error);
            }
        });

        queue.add(stringRequest);

    }


}
