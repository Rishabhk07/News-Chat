package me.rishabhkhanna.newschat.views.Activities;


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

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.ImageView;
import android.widget.TextView;


import com.crashlytics.android.Crashlytics;

import io.fabric.sdk.android.Fabric;
import me.rishabhkhanna.newschat.Network.API;
import me.rishabhkhanna.newschat.Network.interfaces.getAuth;
import me.rishabhkhanna.newschat.model.FcmKey;
import me.rishabhkhanna.newschat.utils.Constants;

import me.rishabhkhanna.newschat.utils.UtilMethods;
import me.rishabhkhanna.newschat.views.Fragments.AllNewsFragment;
import me.rishabhkhanna.newschat.views.Fragments.LoginFragment;
import me.rishabhkhanna.newschat.views.Fragments.NetworkNotConnectedFragment;
import me.rishabhkhanna.newschat.views.Fragments.NewsTopic;
import me.rishabhkhanna.newschat.views.Fragments.ProfileFragment;
import me.rishabhkhanna.newschat.views.Fragments.RateNewFragment;
import me.rishabhkhanna.newschat.views.Fragments.YourNewsFragment;

import com.facebook.AccessToken;

import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginManager;
import com.google.firebase.iid.FirebaseInstanceId;
import com.squareup.picasso.Picasso;


import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
    boolean fromNotification = false;


    @Override
    protected void onNewIntent(Intent intent) {
        thisTab = intent.getStringExtra("notification_key");
        loginRecreate = intent.getIntExtra(Constants.loginFragmentIntent, 0);
        fromNotification = intent.getBooleanExtra("fromNotification", false);
        Log.d(TAG, "onNewIntent: ON NEW INTENT IN BASE");
        if (fromNotification) {
            Intent i = new Intent(this, DetailNewsActivity.class);
            i.putExtra("fromNotification", intent.getBooleanExtra("fromNotification", fromNotification));
            i.putExtra("table_key", intent.getStringExtra("table_key"));
            i.putExtra("news_id", intent.getStringExtra("news_id"));
            startActivity(i);
        }


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

//        Log.d(TAG, "onNewIntent: Recreate" + loginRecreate);
//        Log.d(TAG, "onNewIntent: " + thisTab);
        setFragment(thisTab);
        setProfilePicture();
        super.onNewIntent(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());

        ontop = true;
        fromNotification = getIntent().getBooleanExtra("fromNotification", false);
        if (fromNotification) {
            Intent i = new Intent(this, DetailNewsActivity.class);
            i.putExtra("fromNotification", getIntent().getBooleanExtra("fromNotification", fromNotification));
            i.putExtra("table_key", getIntent().getStringExtra("table_key"));
            i.putExtra("news_id", getIntent().getStringExtra("news_id"));
            startActivity(i);
        }
        setContentView(me.rishabhkhanna.newschat.R.layout.activity_nav_drawer);

        Toolbar toolbar = (Toolbar) findViewById(me.rishabhkhanna.newschat.R.id.toolbar);
        setSupportActionBar(toolbar);
        thisTab = getIntent().getStringExtra("notification_key");
//        loginRecreate = getIntent().getIntExtra(Constants.loginFragmentIntent,0);
//        Log.d(TAG, "onCreate: loginrEcreate: " + loginRecreate);
        DrawerLayout drawer = (DrawerLayout) findViewById(me.rishabhkhanna.newschat.R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, me.rishabhkhanna.newschat.R.string.navigation_drawer_open, me.rishabhkhanna.newschat.R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        Realm.init(this);
        navigationView = (NavigationView) findViewById(me.rishabhkhanna.newschat.R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        setFragment(thisTab);
        setProfilePicture();
        SharedPreferences sharedPreferences = getSharedPreferences(Constants.first_user_DB, MODE_PRIVATE);
        Boolean firstUser = sharedPreferences.getBoolean(Constants.firstUserKey, true);
        if (firstUser) {
            API.getInstance().retrofit.create(getAuth.class).saveFcm(
                    FirebaseInstanceId.getInstance().getToken()
            ).enqueue(new Callback<FcmKey>() {
                @Override
                public void onResponse(Call<FcmKey> call, Response<FcmKey> response) {
//                    Log.d(TAG, "onResponse: " + response.body().getFcmKey());
//                    Log.d(TAG, "onResponse: registered"  );
                }

                @Override
                public void onFailure(Call<FcmKey> call, Throwable t) {

                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(me.rishabhkhanna.newschat.R.id.drawer_layout);
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
        if (id == me.rishabhkhanna.newschat.R.id.nav_rate_news) {
            if (UtilMethods.isNetConnected(BaseActivity.this)) {
                if (token.equals("null") || AccessToken.getCurrentAccessToken() == null) {
                    loginPage = 1;

                    getLoginPage(getResources().getString(me.rishabhkhanna.newschat.R.string.rate_news), loginPage);
                    setTitle("Login");
                } else {
                    fragment = new RateNewFragment();
                    setTitle("Rate News");
                }
            } else {
                fragment = new NetworkNotConnectedFragment();
            }
        } else if (id == me.rishabhkhanna.newschat.R.id.allNews) {
            fragment = new AllNewsFragment();
            setTitle("All News");
        } else if (id == me.rishabhkhanna.newschat.R.id.nav_Topic) {
            if (UtilMethods.isNetConnected(BaseActivity.this)) {
                if (token.equals("null") || AccessToken.getCurrentAccessToken() == null) {
                    loginPage = 3;
                    getLoginPage(getResources().getString(me.rishabhkhanna.newschat.R.string.news_topics), loginPage);
                    setTitle("Login");
                } else {
                    fragment = new NewsTopic();
                    setTitle("Topics");
                }
            } else {
                fragment = new NetworkNotConnectedFragment();
            }
        } else if (id == me.rishabhkhanna.newschat.R.id.nav_your_news) {
            if (UtilMethods.isNetConnected(BaseActivity.this)) {
                if (token.equals("null") || AccessToken.getCurrentAccessToken() == null) {
                    loginPage = 2;
                    getLoginPage(getResources().getString(me.rishabhkhanna.newschat.R.string.your_news), loginPage);
                    setTitle("Login");
                } else {
                    fragment = new YourNewsFragment();
                    setTitle("Your News");
                }
            } else {
                fragment = new NetworkNotConnectedFragment();
            }
        } else if (id == me.rishabhkhanna.newschat.R.id.nav_signout) {
            LoginManager.getInstance().logOut();

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(Constants.LOGIN_TOKEN, "null");
            editor.putString(Constants.LOGIN_USER_ID, "null");
            editor.apply();
            loginRecreate = 0;
            setFragment(null);
            setProfilePicture();
        } else if (id == me.rishabhkhanna.newschat.R.id.nav_profile) {
            if (UtilMethods.isNetConnected(BaseActivity.this)) {
//                Log.d(TAG, "onNavigationItemSelected: " + id);
                navigationView.getMenu().findItem(id).setChecked(true);
                if (token.equals("null") || AccessToken.getCurrentAccessToken() == null) {
                    loginPage = 4;
                    getLoginPage(getResources().getString(me.rishabhkhanna.newschat.R.string.profile), loginPage);
                    setTitle("Login");
                } else {
                    fragment = new ProfileFragment();
                    setTitle("Profile");
                }
            } else {
                fragment = new NetworkNotConnectedFragment();
            }
        }
        fragmentTransaction.replace(me.rishabhkhanna.newschat.R.id.flActivity_main, fragment).commit();
        DrawerLayout drawer = (DrawerLayout) findViewById(me.rishabhkhanna.newschat.R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onStop() {
        ontop = false;
        super.onStop();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        ontop = true;
    }

    @Override
    protected void onStart() {
        ontop = true;
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
        fragmentTransaction.replace(me.rishabhkhanna.newschat.R.id.flActivity_main, fragment).commitAllowingStateLoss();

    }

    public void setProfilePicture() {
        View headerView = navigationView.getHeaderView(0);
        headerImageView = (ImageView) headerView.findViewById(me.rishabhkhanna.newschat.R.id.headerImageView);
        tvHeaderName = (TextView) headerView.findViewById(me.rishabhkhanna.newschat.R.id.tvHeaderName);
        tvHeaderMail = (TextView) headerView.findViewById(me.rishabhkhanna.newschat.R.id.tvHeaderMail);
        if (AccessToken.getCurrentAccessToken() != null) {
            if (Profile.getCurrentProfile() == null) {
                Profile.fetchProfileForCurrentAccessToken();
                ProfileTracker profileTracker = new ProfileTracker() {
                    @Override
                    protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {
                        Profile.setCurrentProfile(currentProfile);
                        Picasso.with(BaseActivity.this).load(currentProfile.getProfilePictureUri(100, 100)).into(headerImageView);
                        tvHeaderName.setText(currentProfile.getName());
                        this.stopTracking();
                    }
                };
                profileTracker.startTracking();
            } else {
                Picasso.with(this).load(Profile.getCurrentProfile().getProfilePictureUri(100, 100)).into(headerImageView);
                tvHeaderName.setText(Profile.getCurrentProfile().getName());
            }

            SharedPreferences sharedPreferences = getSharedPreferences(Constants.AUTH_DETAILS, MODE_PRIVATE);
            String email = sharedPreferences.getString(Constants.AUTH_EMAIL, "");
            tvHeaderMail.setVisibility(View.VISIBLE);
            tvHeaderMail.setText(email);
            navigationView.getMenu().findItem(me.rishabhkhanna.newschat.R.id.nav_signout).setVisible(true);

        } else {
            Picasso.with(this).load(me.rishabhkhanna.newschat.R.drawable.person_placeholder).into(headerImageView);
            tvHeaderName.setText("Guest");
            tvHeaderMail.setVisibility(View.GONE);
            navigationView.getMenu().findItem(me.rishabhkhanna.newschat.R.id.nav_signout).setVisible(false);
        }

    }
}

