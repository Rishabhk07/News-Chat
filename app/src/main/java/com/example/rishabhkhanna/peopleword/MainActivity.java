package com.example.rishabhkhanna.peopleword;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.daprlabs.cardstack.SwipeDeck;
import com.example.rishabhkhanna.peopleword.Adapters.Adapters;
import com.example.rishabhkhanna.peopleword.model.ToiJson;
import com.example.rishabhkhanna.peopleword.utils.FetchNews;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.android.volley.Request.Method.GET;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "fetchNews";
    public static  SwipeDeck swipeDeck;
    public static ArrayList<ToiJson> newsList = new ArrayList<>();
    public static Adapters.SwipeCardAdapter swipeCardAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        swipeDeck = (SwipeDeck) findViewById(R.id.swipe_deck);
        FetchNews.getNewsJson(MainActivity.this);
    }
}



