package me.rishabhkhanna.peopleword.views.Activities;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;


import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;

import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.util.Log;

import android.view.MenuItem;
import android.view.View;

import android.widget.ImageView;
import android.widget.TextView;


import com.crashlytics.android.Crashlytics;
import io.fabric.sdk.android.Fabric;
import io.realm.internal.Util;
import me.rishabhkhanna.peopleword.utils.Constants;

import me.rishabhkhanna.peopleword.utils.UtilMethods;
import me.rishabhkhanna.peopleword.views.Fragments.AllNewsFragment;
import me.rishabhkhanna.peopleword.views.Fragments.LoginFragment;
import me.rishabhkhanna.peopleword.views.Fragments.NetworkNotConnectedFragment;
import me.rishabhkhanna.peopleword.views.Fragments.NewsTopic;
import me.rishabhkhanna.peopleword.views.Fragments.ProfileFragment;
import me.rishabhkhanna.peopleword.views.Fragments.RateNewFragment;
import me.rishabhkhanna.peopleword.views.Fragments.YourNewsFragment;
import com.facebook.AccessToken;

import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.squareup.picasso.Picasso;


import io.realm.Realm;

public class BaseActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static final String TAG = "BaseActivity";
    public Fragment fragment;
    String thisTab = null;
    Boolean ontop = false;
    ImageView headerImageView;
    TextView tvHeaderName;
    TextView tvHeaderMail;
    NavigationView navigationView;
    int loginRecreate = 0;


    @Override
    protected void onNewIntent(Intent intent) {
        thisTab = intent.getStringExtra("notification_key");
        loginRecreate = intent.getIntExtra(Constants.loginFragmentIntent, 0);

//
//        Log.d(TAG, "onNewIntent: " + thisTab);
//        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
//        Log.d(TAG, "onCreate: " + thisTab);
//        if (thisTab != null) {
//            fragment = AllNewsFragment.getInstance(thisTab);
//        } else {
//            fragment = new AllNewsFragment();
//        }
//        fragmentTransaction.replace(R.id.flActivity_main, fragment).commitAllowingStateLoss();

        Log.d(TAG, "onNewIntent: Recreate" + loginRecreate);
        setFragment(thisTab);
        setProfilePicture();
        super.onNewIntent(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        ontop = true;
        setContentView(me.rishabhkhanna.peopleword.R.layout.activity_nav_drawer);
        Log.d(TAG, "onCreate: ");
        Toolbar toolbar = (Toolbar) findViewById(me.rishabhkhanna.peopleword.R.id.toolbar);
        setSupportActionBar(toolbar);
        thisTab = getIntent().getStringExtra("notification_key");
//        loginRecreate = getIntent().getIntExtra(Constants.loginFragmentIntent,0);
        Log.d(TAG, "onCreate: loginrEcreate: " + loginRecreate);
        DrawerLayout drawer = (DrawerLayout) findViewById(me.rishabhkhanna.peopleword.R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, me.rishabhkhanna.peopleword.R.string.navigation_drawer_open, me.rishabhkhanna.peopleword.R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        Realm.init(this);
        navigationView = (NavigationView) findViewById(me.rishabhkhanna.peopleword.R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        Log.d(TAG, "onCreate: " + thisTab);
        setFragment(thisTab);
        setProfilePicture();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(me.rishabhkhanna.peopleword.R.id.drawer_layout);
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
        SharedPreferences sharedPreferences = getSharedPreferences(Constants.AUTH_DETAILS, Context.MODE_PRIVATE);
        String token = sharedPreferences.getString(Constants.LOGIN_TOKEN, "null");
        int loginPage = 0;
        if (id == me.rishabhkhanna.peopleword.R.id.nav_rate_news) {
            if(UtilMethods.isNetConnected(BaseActivity.this)){
                if (token.equals("null") || AccessToken.getCurrentAccessToken() == null) {
                    loginPage = 1;

                    getLoginPage(getResources().getString(me.rishabhkhanna.peopleword.R.string.rate_news),loginPage);
                    setTitle("Login");
                } else {
                    fragment = new RateNewFragment();
                    setTitle("Rate News");
                }
            }else{
                fragment = new NetworkNotConnectedFragment();
            }


        } else if (id == me.rishabhkhanna.peopleword.R.id.allNews) {
            fragment = new AllNewsFragment();
            setTitle("All News");
        } else if (id == me.rishabhkhanna.peopleword.R.id.nav_Topic) {
            if(UtilMethods.isNetConnected(BaseActivity.this)) {
                if (token.equals("null") || AccessToken.getCurrentAccessToken() == null) {
                    loginPage = 3;
                    getLoginPage(getResources().getString(me.rishabhkhanna.peopleword.R.string.news_topics), loginPage);
                    setTitle("Login");
                } else {
                    fragment = new NewsTopic();
                    setTitle("Topics");
                }
            }else{
                fragment = new NetworkNotConnectedFragment();
            }
        } else if (id == me.rishabhkhanna.peopleword.R.id.nav_your_news) {
            if(UtilMethods.isNetConnected(BaseActivity.this)) {
                if (token.equals("null") || AccessToken.getCurrentAccessToken() == null) {
                    loginPage = 2;
                    getLoginPage(getResources().getString(me.rishabhkhanna.peopleword.R.string.your_news), loginPage);
                    setTitle("Login");
                } else {
                    fragment = new YourNewsFragment();
                    setTitle("Your News");
                }
            }else{
                fragment = new NetworkNotConnectedFragment();
            }
        } else if (id == me.rishabhkhanna.peopleword.R.id.nav_signout) {
            LoginManager.getInstance().logOut();

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(Constants.LOGIN_TOKEN, "null");
            editor.putString(Constants.LOGIN_USER_ID, "null");
            editor.apply();
            loginRecreate = 0;
            setFragment(null);
            setProfilePicture();
        } else if (id == me.rishabhkhanna.peopleword.R.id.nav_profile) {
            if(UtilMethods.isNetConnected(BaseActivity.this)) {
                Log.d(TAG, "onNavigationItemSelected: " + id);
                navigationView.getMenu().findItem(id).setChecked(true);
                if (token.equals("null") || AccessToken.getCurrentAccessToken() == null) {
                    loginPage = 4;
                    getLoginPage(getResources().getString(me.rishabhkhanna.peopleword.R.string.profile), loginPage);
                    setTitle("Login");
                } else {
                    fragment = new ProfileFragment();
                    setTitle("Profile");
                }
            }else{
                fragment = new NetworkNotConnectedFragment();
            }
        }
        fragmentTransaction.replace(me.rishabhkhanna.peopleword.R.id.flActivity_main, fragment).commit();
        DrawerLayout drawer = (DrawerLayout) findViewById(me.rishabhkhanna.peopleword.R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onStop() {
        Log.d(TAG, "onStop: ");
        ontop = false;
        super.onStop();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        ontop = true;
        Log.d(TAG, "onRestart: ");
    }

    @Override
    protected void onStart() {
        ontop = true;
        Log.d(TAG, "onResume: " + ontop);
        super.onStart();
    }

    private void getLoginPage(String intro, int page) {
        fragment = LoginFragment.newInstance(intro, page);
    }

    private void setFragment(String thisTab) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        switch (loginRecreate) {
            case 0:
                if (thisTab != null) {
                    fragment = AllNewsFragment.getInstance(thisTab);
                } else {
                    fragment = new AllNewsFragment();
                }
                navigationView.getMenu().getItem(0).setChecked(true);
                setTitle("All News");
                break;
            case 1:
                fragment = new RateNewFragment();
                setTitle("Rate News");
                break;
            case 2:
                fragment = new YourNewsFragment();
                setTitle("Your News");
                break;
            case 3:
                fragment = new NewsTopic();
                setTitle("Topics");
                break;
            case 4:
                fragment = new ProfileFragment();
                setTitle("Profile");
                break;
        }
        fragmentTransaction.replace(me.rishabhkhanna.peopleword.R.id.flActivity_main, fragment).commitAllowingStateLoss();

    }

    public void setProfilePicture() {
        View headerView = navigationView.getHeaderView(0);
        headerImageView = (ImageView) headerView.findViewById(me.rishabhkhanna.peopleword.R.id.headerImageView);
        tvHeaderName = (TextView) headerView.findViewById(me.rishabhkhanna.peopleword.R.id.tvHeaderName);
        tvHeaderMail = (TextView) headerView.findViewById(me.rishabhkhanna.peopleword.R.id.tvHeaderMail);
        if (AccessToken.getCurrentAccessToken() != null) {
            Picasso.with(this).load(Profile.getCurrentProfile().getProfilePictureUri(100, 100)).into(headerImageView);
            tvHeaderName.setText(Profile.getCurrentProfile().getName());
            SharedPreferences sharedPreferences = getSharedPreferences(Constants.AUTH_DETAILS, MODE_PRIVATE);
            String email = sharedPreferences.getString(Constants.AUTH_EMAIL, "");
            tvHeaderMail.setVisibility(View.VISIBLE);
            tvHeaderMail.setText(email);
            navigationView.getMenu().findItem(me.rishabhkhanna.peopleword.R.id.nav_signout).setVisible(true);

        } else {
            Picasso.with(this).load(me.rishabhkhanna.peopleword.R.drawable.person_placeholder).into(headerImageView);
            tvHeaderName.setText("Guest");
            tvHeaderMail.setVisibility(View.GONE);
            navigationView.getMenu().findItem(me.rishabhkhanna.peopleword.R.id.nav_signout).setVisible(false);
        }

    }
}
