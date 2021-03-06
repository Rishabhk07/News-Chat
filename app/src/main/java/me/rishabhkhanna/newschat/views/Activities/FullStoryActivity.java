package me.rishabhkhanna.newschat.views.Activities;

import android.os.Build;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import me.rishabhkhanna.newschat.model.NewsJson;
import me.rishabhkhanna.newschat.utils.Constants;
import me.rishabhkhanna.newschat.utils.UtilMethods;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;


public class FullStoryActivity extends AppCompatActivity {

    ImageView ivFullStory;
    TextView tvFullStory;
    AppBarLayout appBarLayout;
    Boolean appBarExtented = true;
    Toolbar toolbar;
    CollapsingToolbarLayout collapsingToolbarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(me.rishabhkhanna.newschat.R.layout.full_story_layout);
        appBarLayout = (AppBarLayout) findViewById(me.rishabhkhanna.newschat.R.id.app_bar);
        ivFullStory = (ImageView) findViewById(me.rishabhkhanna.newschat.R.id.ivFullStory);
        tvFullStory = (TextView) findViewById(me.rishabhkhanna.newschat.R.id.tvFullStory);
        toolbar = (Toolbar) findViewById(me.rishabhkhanna.newschat.R.id.toolbar);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(me.rishabhkhanna.newschat.R.id.ctFullStory);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        Gson gson = new Gson();
        NewsJson thisNews = gson.fromJson(getIntent().getStringExtra(Constants.FULL_STORY_KEY), NewsJson.class);
        Picasso.with(this)
                .load(UtilMethods.getImageurl(thisNews.getImageid(),"1400","960"))
                .fit()
                .into(ivFullStory);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            if (thisNews.getStory() != null) {
                tvFullStory.setText(Html.fromHtml(thisNews.getStory(), Html.FROM_HTML_MODE_COMPACT));
            }
        } else {
            if(thisNews.getStory() != null) {
                tvFullStory.setText(Html.fromHtml(thisNews.getStory()));
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }
}
