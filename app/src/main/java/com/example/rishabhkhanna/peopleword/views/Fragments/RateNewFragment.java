package com.example.rishabhkhanna.peopleword.views.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.daprlabs.cardstack.SwipeDeck;
import com.example.rishabhkhanna.peopleword.Adapters.RateNewsAdapter;
import com.example.rishabhkhanna.peopleword.Interfaces.onJsonRecieved;
import com.example.rishabhkhanna.peopleword.R;
import com.example.rishabhkhanna.peopleword.model.ToiJson;
import com.example.rishabhkhanna.peopleword.utils.FetchNews;
import com.example.rishabhkhanna.peopleword.views.Activities.BaseActivity;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class RateNewFragment extends Fragment {

    private SwipeDeck swipeDeck;
    public  RateNewsAdapter.SwipeCardAdapter swipeCardAdapter;
    Button dislikeBtn , likeBtn;
    ArrayList<ToiJson> newsArrayList = new ArrayList<>();
    public static final String TAG = "RateNewsActivity";
    public RateNewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_rate_new, container, false);
        swipeDeck = (SwipeDeck) root.findViewById(R.id.swipe_deck);
        likeBtn = (Button) root.findViewById(R.id.like_btn);
        dislikeBtn = (Button) root.findViewById(R.id.dislike_btn);

        swipeCardAdapter = RateNewsAdapter.getSwipeCardAdapter(newsArrayList, getContext());
        swipeDeck.setAdapter(swipeCardAdapter);
        //Interface callback to show data after download
        onJsonRecieved onJsonRecieved = new onJsonRecieved() {
            @Override
            public void onSuccess(ArrayList<ToiJson> fetchedNewsList) {
                newsArrayList.addAll(fetchedNewsList);
                swipeCardAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(VolleyError error) {
                Log.d(TAG, "onError: "  + error);
                Toast.makeText(getActivity(), "Sorry could not fetch news at this moment", Toast.LENGTH_SHORT).show();
            }
        };

        //get Toi data
        FetchNews.getNewsJson(getActivity(),onJsonRecieved);

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




        return root;
    }

}
