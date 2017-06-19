package com.example.rishabhkhanna.peopleword.views.Activities;


import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.daprlabs.cardstack.SwipeDeck;
import com.example.rishabhkhanna.peopleword.Adapters.Adapters;
import com.example.rishabhkhanna.peopleword.Interfaces.onJsonRecieved;
import com.example.rishabhkhanna.peopleword.R;
import com.example.rishabhkhanna.peopleword.model.ToiJson;
import com.example.rishabhkhanna.peopleword.utils.FetchNews;

import java.util.ArrayList;

public class BaseActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    public static final String TAG = "BaseActivity";

    private SwipeDeck swipeDeck;
    public  Adapters.SwipeCardAdapter swipeCardAdapter;
    Button dislikeBtn , likeBtn;
    ArrayList<ToiJson> newsArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_drawer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Briefs");
        swipeDeck = (SwipeDeck) findViewById(R.id.swipe_deck);
        likeBtn = (Button) findViewById(R.id.like_btn);
        dislikeBtn = (Button) findViewById(R.id.dislike_btn);

        swipeCardAdapter = Adapters.getSwipeCardAdapter(newsArrayList, this);
        swipeDeck.setAdapter(swipeCardAdapter);


        //interface on receiveing data
        onJsonRecieved onJsonRecieved = new onJsonRecieved() {
            @Override
            public void onSuccess(ArrayList<ToiJson> fetchedNewsList) {
                newsArrayList.addAll(fetchedNewsList);
                swipeCardAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(VolleyError error) {
                Log.d(TAG, "onError: "  + error);
                Toast.makeText(BaseActivity.this, "Sorry could not fetch news at this moment", Toast.LENGTH_SHORT).show();
            }
        };

        //get Toi data
        FetchNews.getNewsJson(BaseActivity.this,onJsonRecieved);

        DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();


        Display display = getWindowManager().getDefaultDisplay();
        Log.d(TAG, "onCreate: height"  + displayMetrics.heightPixels/displayMetrics.density );
        Log.d(TAG, "onCreate: width"  + displayMetrics.widthPixels / displayMetrics.density );



        //button click listner
        likeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                swipeDeck.swipeTopCardRight(1000);
            }
        });

        dislikeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                swipeDeck.swipeTopCardLeft(1000);
            }
        });

        // set images on of like and dislike on swipe
        swipeDeck.setLeftImage(R.id.nope_card_image);
        swipeDeck.setRightImage(R.id.like_card_image);

        DrawerLayout drawer =  (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_news) {
            // Handle the camera action
        } else if (id == R.id.nav_word) {

        } else if (id == R.id.nav_Topic) {

        } else if (id == R.id.nav_rating) {

        } else if (id == R.id.nav_edit) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
