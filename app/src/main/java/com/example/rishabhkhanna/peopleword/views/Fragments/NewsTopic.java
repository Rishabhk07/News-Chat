package com.example.rishabhkhanna.peopleword.views.Fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rishabhkhanna.peopleword.Adapters.TopicAdapter;
import com.example.rishabhkhanna.peopleword.Interfaces.ItemTouchHelperAdapter;
import com.example.rishabhkhanna.peopleword.Interfaces.TouchHelper;
import com.example.rishabhkhanna.peopleword.R;
import com.example.rishabhkhanna.peopleword.utils.UtilMethods;
import com.google.android.flexbox.AlignContent;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewsTopic extends Fragment {


    RecyclerView recyclerViewTopic;
    public NewsTopic() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_news_topic, container, false);
        recyclerViewTopic = (RecyclerView) root.findViewById(R.id.rvTopic);
        TopicAdapter topicAdapter = new TopicAdapter(getContext(), UtilMethods.getTopics());
        FlexboxLayoutManager flexboxLayoutManager = new FlexboxLayoutManager(getContext());
        flexboxLayoutManager.setFlexWrap(FlexWrap.WRAP);

        flexboxLayoutManager.setJustifyContent(JustifyContent.CENTER);
        recyclerViewTopic.setLayoutManager(flexboxLayoutManager);
        recyclerViewTopic.setAdapter(topicAdapter);
        ItemTouchHelper.Callback callback = new TouchHelper(topicAdapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(recyclerViewTopic);
        return root;
    }

}
