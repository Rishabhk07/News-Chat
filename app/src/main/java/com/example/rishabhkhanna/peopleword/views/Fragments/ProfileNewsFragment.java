package com.example.rishabhkhanna.peopleword.views.Fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.rishabhkhanna.peopleword.Adapters.AllNewsPageRecyclerAdapter;
import com.example.rishabhkhanna.peopleword.Network.API;
import com.example.rishabhkhanna.peopleword.Network.interfaces.rateNews;
import com.example.rishabhkhanna.peopleword.R;
import com.example.rishabhkhanna.peopleword.model.NewsJson;
import com.example.rishabhkhanna.peopleword.model.RatedNewsPojo;
import com.example.rishabhkhanna.peopleword.utils.Constants;
import com.facebook.AccessToken;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by rishabhkhanna on 13/08/17.
 */

public class ProfileNewsFragment extends Fragment {
    public ProfileNewsFragment() {
        // empty constructor is required in fragment to imflate it
    }
    int userNewsinfo;
    RecyclerView recyclerView;
    ArrayList<NewsJson> userNews = new ArrayList<>();
    public static final String TAG = "ProfileNewsFragment";
    public static ProfileNewsFragment getInstance(int url){
        ProfileNewsFragment profileNewsFragment = new ProfileNewsFragment();
        Bundle args = new Bundle();
        args.putInt(Constants.PROFILE_NEWS,url);
        profileNewsFragment.setArguments(args);
        return profileNewsFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null){
            userNewsinfo = getArguments().getInt(Constants.PROFILE_NEWS);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile_rating,container,false);
        recyclerView = (RecyclerView) view.findViewById(R.id.rvProfileRate);
        AllNewsPageRecyclerAdapter adapter = new AllNewsPageRecyclerAdapter(userNews,getActivity());
        if(AccessToken.getCurrentAccessToken() != null){
            API.getInstance().retrofit.create(rateNews.class).getRatedNews(
                AccessToken.getCurrentAccessToken().getUserId()
            ).enqueue(new Callback<RatedNewsPojo>() {
                @Override
                public void onResponse(Call<RatedNewsPojo> call, Response<RatedNewsPojo> response) {
                    Log.d(TAG, "onResponse: " + response.body().getBriefs().get(0).getmUserTable().getRating());
                    Log.d(TAG, "onResponse: " + response.body().getBriefs().get(0).getHl());
                }
                @Override
                public void onFailure(Call<RatedNewsPojo> call, Throwable t) {

                }
            });
        }else {
            Toast.makeText(getContext(), "you are not loggedin", Toast.LENGTH_SHORT).show();
        }


        return view;
    }


}
