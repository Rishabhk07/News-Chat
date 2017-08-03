package com.example.rishabhkhanna.peopleword.views.Activities;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.daprlabs.cardstack.SwipeDeck;
import com.example.rishabhkhanna.peopleword.Adapters.RateNewsAdapter;
import com.example.rishabhkhanna.peopleword.Interfaces.onJsonRecieved;
import com.example.rishabhkhanna.peopleword.R;
import com.example.rishabhkhanna.peopleword.model.ToiJson;
import com.example.rishabhkhanna.peopleword.utils.Constants;
import com.example.rishabhkhanna.peopleword.utils.FetchNews;
import com.example.rishabhkhanna.peopleword.views.Fragments.AllNewsFragment;
import com.example.rishabhkhanna.peopleword.views.Fragments.NewsTopic;
import com.example.rishabhkhanna.peopleword.views.Fragments.RateNewFragment;
import com.example.rishabhkhanna.peopleword.views.Fragments.YourNewsFragment;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.login.LoginManager;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Signature;
import java.util.ArrayList;

import io.realm.Realm;

public class BaseActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    public static final String TAG = "BaseActivity";
    public Fragment fragment;
    String thisTab = null;
    Boolean ontop = false;

    @Override
    protected void onStop() {
        Log.d(TAG, "onStop: ");
        ontop = false;
        super.onStop();
    }

    @Override
    protected void onStart() {
        ontop = true;
        Log.d(TAG, "onResume: " + ontop);
        super.onStart();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        thisTab = intent.getStringExtra("notification_key");
        if(ontop) {
            Log.d(TAG, "onNewIntent: " + thisTab);
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            Log.d(TAG, "onCreate: " + thisTab);
            if (thisTab != null) {
                fragment = AllNewsFragment.getInstance(thisTab);
            } else {
                fragment = new AllNewsFragment();
            }
            fragmentTransaction.replace(R.id.flActivity_main, fragment).commit();
        }
        super.onNewIntent(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_drawer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Briefs");
        thisTab = getIntent().getStringExtra("notification_key");

        DrawerLayout drawer =  (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        Realm.init(this);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        Log.d(TAG, "onCreate: "  + thisTab);
        if(thisTab != null){
            fragment = AllNewsFragment.getInstance(thisTab);
        }else{
            fragment = new AllNewsFragment();
        }

        fragmentTransaction.replace(R.id.flActivity_main,fragment).commit();
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
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        if (id == R.id.nav_rate_news) {

            fragment = new RateNewFragment();

        } else if (id == R.id.allNews) {

             fragment = new AllNewsFragment();

        } else if (id == R.id.nav_Topic) {

            fragment = new NewsTopic();

        } else if (id == R.id.nav_your_news) {
            fragment = new YourNewsFragment();
        } else if (id == R.id.nav_signout) {
            LoginManager.getInstance().logOut();
            SharedPreferences sharedPreferences = getSharedPreferences(Constants.AUTH_DETAILS, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(Constants.LOGIN_TOKEN,"null");
            editor.putString(Constants.LOGIN_USER_ID,"null");
            editor.apply();
            fragment = new RateNewFragment();
        }
        fragmentTransaction.replace(R.id.flActivity_main,fragment).commit();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
