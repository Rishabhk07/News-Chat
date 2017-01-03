package com.example.rishabhkhanna.peopleword.views;


import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.daprlabs.cardstack.SwipeDeck;
import com.example.rishabhkhanna.peopleword.Adapters.Adapters;
import com.example.rishabhkhanna.peopleword.R;
import com.example.rishabhkhanna.peopleword.model.ToiJson;
import com.example.rishabhkhanna.peopleword.utils.FetchNews;

import java.util.ArrayList;

public class NavDrawer extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

   public static SwipeDeck swipeDeck;
    public static Adapters.SwipeCardAdapter swipeCardAdapter;
    Button dislikeBtn , likeBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_drawer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //get Toi data
        FetchNews.getNewsJson(NavDrawer.this);
        swipeDeck = (SwipeDeck) findViewById(R.id.swipe_deck);
        likeBtn = (Button) findViewById(R.id.like_btn);
        dislikeBtn = (Button) findViewById(R.id.dislike_btn);

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



        //

        DrawerLayout drawer =  (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


    }

    public static void attachAdapter(ArrayList<ToiJson> newsList, Context context) {

        swipeCardAdapter = Adapters.getSwipeCardAdapter(newsList, context);
        swipeDeck.setLeftImage(R.id.nope_card_image);
        swipeDeck.setRightImage(R.id.like_card_image);
        swipeDeck.setAdapter(swipeCardAdapter);


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
