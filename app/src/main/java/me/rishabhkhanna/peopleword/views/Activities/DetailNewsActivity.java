package me.rishabhkhanna.peopleword.views.Activities;

import android.Manifest;
import android.animation.Animator;

import android.annotation.TargetApi;
import android.content.Intent;

import android.content.pm.PackageManager;
import android.graphics.Bitmap;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;

import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.customtabs.CustomTabsIntent;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;


import me.rishabhkhanna.peopleword.Network.API;
import me.rishabhkhanna.peopleword.Network.interfaces.getNotificationNews;
import me.rishabhkhanna.peopleword.R;

import me.rishabhkhanna.peopleword.model.NewsJson;

import me.rishabhkhanna.peopleword.utils.Constants;

import me.rishabhkhanna.peopleword.utils.UtilMethods;
import me.rishabhkhanna.peopleword.views.Fragments.LoginFragment;
import retrofit2.Call;
import retrofit2.Response;

import com.facebook.AccessToken;
import com.google.gson.Gson;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;


public class DetailNewsActivity extends AppCompatActivity {

    public static final String TAG = "DetailNew Activity";
    TextView headTv;
    TextView detailTV;
    ImageView imageViewNews;
    BottomNavigationView navigationView;
    LinearLayout linearLayout;
    ProgressBar progressBar;
    TextView tvSource;
    RelativeLayout relativeLayout;
    FrameLayout frameLayoutreplace;
    boolean shareProgress = false;
    boolean chatLogin = false;
    int PERM_REQ  = 1234;
    Boolean fromNotification = false;
    NewsJson thisNews;
    ProgressBar progressBarNotification;


    private void getThisNews(String msid,String news_id) {
        API.getInstance()
                .retrofit
                .create(getNotificationNews.class)
                .getThisNews(msid,news_id)
                .enqueue(new retrofit2.Callback<NewsJson>() {
                    @Override
                    public void onResponse(Call<NewsJson> call, Response<NewsJson> response) {
                        progressBarNotification.setVisibility(View.GONE);
                        thisNews = response.body();
                        Log.d(TAG, "onResponse: " + call.request());
                        setData();
                    }

                    @Override
                    public void onFailure(Call<NewsJson> call, Throwable t) {
                        Log.d(TAG, "onFailure: " + call.request());
                    }
                });
    }


    public void setData(){
        headTv.setText(thisNews.getHl());
        detailTV.setText(thisNews.getSyn());
        final Gson gson = new Gson();
        Log.d(TAG, "setData: " + thisNews.getImageid());
        Picasso.with(DetailNewsActivity.this)
                .load(UtilMethods.getImageurl(thisNews.getImageid(),"1400","960"))
                .fit()
                .centerCrop()
                .noFade()
                .into(imageViewNews, new Callback() {
                    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public void onSuccess() {
                        try {
                            imageViewNews.setVisibility(View.VISIBLE);
                            headTv.setVisibility(View.VISIBLE);
                            detailTV.setVisibility(View.VISIBLE);
                            tvSource.setVisibility(View.VISIBLE);
                            progressBar.setVisibility(View.GONE);
                            Log.d(TAG, "onSuccess: " + imageViewNews.getWidth() / 2);
                            Log.d(TAG, "onSuccess: " + imageViewNews.getHeight() / 2);

                            int cx = imageViewNews.getWidth() / 2;
                            int cy = imageViewNews.getHeight() / 2;
                            int finalRadius = imageViewNews.getHeight();
                            Animator anim = null;
                            anim = ViewAnimationUtils.createCircularReveal(imageViewNews, cx, cy, 0, finalRadius);
                            anim.setDuration(500);
                            anim.start();
                            Log.d(TAG, "onSuccess: ");
                        } catch (IllegalStateException e) {
                            Log.d(TAG, "onSuccess:  " + e.getMessage());

                        }
                    }

                    @Override
                    public void onError() {

                    }
                });


        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_article:
                        if (thisNews.getTn().equals("photostory")) {
                            Intent i = new Intent(DetailNewsActivity.this, PhotoStoryActivity.class);
                            i.putExtra(Constants.FULL_STORY_KEY, gson.toJson(thisNews.getPhotoStory()));

                            startActivity(i);
                        } else {
                            Intent i = new Intent(DetailNewsActivity.this, FullStoryActivity.class);
                            i.putExtra(Constants.FULL_STORY_KEY, gson.toJson(thisNews));

                            startActivity(i);
                        }
                        break;
                    case R.id.action_share:
                        if(isPermissionGranted()){
                            if (!shareProgress) {
                                shareScreen();
                            }
                        }
                        break;
                    case R.id.action_chat:

                        if (AccessToken.getCurrentAccessToken() != null) {
                            Intent i = new Intent(DetailNewsActivity.this, ChatActivity.class);
                            i.putExtra(Constants.CHAT_KEY, new Gson().toJson(thisNews));
                            i.putExtra(Constants.DETAIL_NEWS_KEY, new Gson().toJson(thisNews));
                            ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(
                                    DetailNewsActivity.this,
                                    Pair.create((View) headTv, "transHeading"));
                            startActivity(i, optionsCompat.toBundle());
                        } else {
                            chatLogin = true;
                            frameLayoutreplace.setVisibility(View.VISIBLE);
                            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                            fragmentTransaction.replace(R.id.framelayoutReplace,
                                    LoginFragment.newInstance(getResources().getString(R.string.chat), 5)).commit();
                            navigationView.setVisibility(View.GONE);
                        }
                        break;
                }
                return true;
            }
        });

        tvSource.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
                CustomTabsIntent customTabsIntent = builder.build();
                customTabsIntent.launchUrl(DetailNewsActivity.this, Uri.parse(thisNews.getSu()));
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_news);
        Log.d(TAG, "onCreate: ONCREATE CALL HO GYA");
        linearLayout = (LinearLayout) findViewById(R.id.llDetailNews);
        headTv = (TextView) findViewById(R.id.news_headline_full);
        detailTV = (TextView) findViewById(R.id.news_deatil_full);
        imageViewNews = (ImageView) findViewById(R.id.detail_image);
        progressBarNotification = (ProgressBar) findViewById(R.id.progressBarNotificaiton);
        navigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        tvSource = (TextView) findViewById(R.id.tvSource);
        relativeLayout = (RelativeLayout) findViewById(R.id.detail_news_relative);
        frameLayoutreplace = (FrameLayout) findViewById(R.id.framelayoutReplace);
        Intent i = getIntent();
        final Gson gson = new Gson();

        thisNews = gson.fromJson(i.getStringExtra(Constants.DETAIL_NEWS_KEY), NewsJson.class);
        Log.d(TAG, "onCreate: " + thisNews);
        fromNotification = getIntent().getBooleanExtra("fromNotification",false);
        if(fromNotification){
            Log.d(TAG, "onCreate: YAHAN KA INTENT CALL HO RHA HAI");
            if(thisNews == null){
                String msid = getIntent().getStringExtra("table_key");
                String news_id = getIntent().getStringExtra("news_id");
                getThisNews(msid,news_id);
            }
        }
        if(thisNews != null) {
            setData();
        }else{
            progressBarNotification.setVisibility(View.VISIBLE);
            imageViewNews.setVisibility(View.INVISIBLE);
            headTv.setVisibility(View.INVISIBLE);
            detailTV.setVisibility(View.INVISIBLE);
            tvSource.setVisibility(View.INVISIBLE);
        }
    }

    private Boolean isPermissionGranted() {

        int perRead = ContextCompat.checkSelfPermission(DetailNewsActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE);
        int perWrite = ContextCompat.checkSelfPermission(DetailNewsActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);


        if(perRead == PackageManager.PERMISSION_GRANTED && perWrite == PackageManager.PERMISSION_GRANTED){
            return true;
        }else{
            ActivityCompat.requestPermissions(
                    DetailNewsActivity.this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    PERM_REQ
            );
            return false;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d(TAG, "onRequestPermissionsResult: " + requestCode);
        if(requestCode == PERM_REQ){
         if(grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED){
             shareScreen();
         }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void shareScreen() {
        shareProgress = true;
        progressBar.setIndeterminate(true);
        progressBar.setVisibility(View.VISIBLE);
        linearLayout.setDrawingCacheEnabled(true);
        final Bitmap bitmap = linearLayout.getDrawingCache();
        new AsyncTask<Void, Void, Uri>() {
            @Override
            protected Uri doInBackground(Void... params) {
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
//                bitmap.compress(Bitmap.CompressFormat.JPEG,100,bytes);
                String path = MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, "Social News", null);
                Uri uri = Uri.parse(path);
                return uri;
            }

            @Override
            protected void onPostExecute(Uri uri) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("image/jpeg");
                intent.putExtra(Intent.EXTRA_STREAM, uri);
                intent.putExtra(Intent.EXTRA_TEXT, "Download the Social News app now");
                linearLayout.setDrawingCacheEnabled(false);

                startActivity(Intent.createChooser(intent, "share using .."));

                progressBar.setIndeterminate(false);
                super.onPostExecute(uri);
                shareProgress = false;
                progressBar.setVisibility(View.GONE);
            }
        }.execute();

    }

    @Override
    public void onBackPressed() {
        if (chatLogin) {
            frameLayoutreplace.setVisibility(View.GONE);
            navigationView.setVisibility(View.VISIBLE);
            chatLogin = false;
        } else {
            super.onBackPressed();
        }
    }


}
