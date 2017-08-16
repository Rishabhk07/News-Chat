package com.example.rishabhkhanna.peopleword.views.Activities;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.rishabhkhanna.peopleword.Adapters.PhotoStoryAdapter;
import com.example.rishabhkhanna.peopleword.R;
import com.example.rishabhkhanna.peopleword.model.PhotoStory;
import com.example.rishabhkhanna.peopleword.utils.Constants;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;

public class PhotoStoryActivity extends AppCompatActivity {
    RecyclerView rvPhotoStory;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_story);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setTitle("Photo Story");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        Gson gson = new Gson();
        PhotoStory[] thisPhotoStory = gson.fromJson(getIntent().getStringExtra(Constants.FULL_STORY_KEY), PhotoStory[].class);
        if (thisPhotoStory != null) {
            ArrayList<PhotoStory> photoStoryArrayList = new ArrayList<>(Arrays.asList(thisPhotoStory));
            rvPhotoStory = (RecyclerView) findViewById(R.id.rvPhotoStory);
            rvPhotoStory.setLayoutManager(new LinearLayoutManager(this));
            PhotoStoryAdapter photoStoryAdapter = new PhotoStoryAdapter(this, photoStoryArrayList);
            rvPhotoStory.setAdapter(photoStoryAdapter);
        }
    }
}
