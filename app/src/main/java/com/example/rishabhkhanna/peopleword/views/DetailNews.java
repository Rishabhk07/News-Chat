package com.example.rishabhkhanna.peopleword.views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rishabhkhanna.peopleword.R;
import com.squareup.picasso.Picasso;

public class DetailNews extends AppCompatActivity {

    public static final String TAG  = "DetailNew Activity";
    TextView headTv ;
    TextView detailTV;
    ImageView imageViewNews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_news);
        headTv = (TextView) findViewById(R.id.news_headline_full);
        detailTV = (TextView) findViewById(R.id.news_deatil_full);
        imageViewNews = (ImageView) findViewById(R.id.detail_image);

        Intent i = getIntent();
        String head = i.getStringExtra("head");
        String detail= i.getStringExtra("detail");
        String img = i.getStringExtra("img");
        Log.d(TAG, "onCreate: " + head);
        Log.d(TAG, "onCreate: " + detail);
        Log.d(TAG, "onCreate: " + img);
        headTv.setText(head);
        detailTV.setText(detail);
        Picasso.with(DetailNews.this).load("http://timesofindia.indiatimes.com/thumb.cms?photoid=" + img + "&width=1500&height=1440&resizemode=1").fit().into(imageViewNews);
    }
}
