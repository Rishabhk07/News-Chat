package me.rishabhkhanna.newschat.views.Fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import me.rishabhkhanna.newschat.Adapters.AllNewsPageRecyclerAdapter;
import me.rishabhkhanna.newschat.Network.API;
import me.rishabhkhanna.newschat.Network.interfaces.rateNews;
import me.rishabhkhanna.newschat.model.NewsJson;
import me.rishabhkhanna.newschat.model.RatedNewsPojo;
import me.rishabhkhanna.newschat.utils.Constants;
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
    int position;
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
            position = getArguments().getInt(Constants.PROFILE_NEWS);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(me.rishabhkhanna.newschat.R.layout.profile_rating,container,false);
        recyclerView = (RecyclerView) view.findViewById(me.rishabhkhanna.newschat.R.id.rvProfileRate);
//        final ProfilePageAdapter adapter = new ProfilePageAdapter(userNews,getActivity(),userNewsinfo);
        final AllNewsPageRecyclerAdapter adapter = new AllNewsPageRecyclerAdapter(userNews,getActivity());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);

        if(AccessToken.getCurrentAccessToken() != null){
            setupPageCall(position).enqueue(new Callback<RatedNewsPojo>() {
                @Override
                public void onResponse(Call<RatedNewsPojo> call, Response<RatedNewsPojo> response) {
                    userNews.clear();
                    userNews.addAll(response.body().getBriefs());
                    adapter.notifyDataSetChanged();
//                    Log.d(TAG, "onResponse: " + call.request());
                }
                @Override
                public void onFailure(Call<RatedNewsPojo> call, Throwable t) {
//                    Log.d(TAG, "onFailure: ");
                }
            });
        }else {
            Toast.makeText(getContext(), "you are not loggedin", Toast.LENGTH_SHORT).show();
        }
        return view;
    }

    public Call<RatedNewsPojo> setupPageCall(int position) {
        if (AccessToken.getCurrentAccessToken() != null) {
            switch (position) {
                case 0:
                    return API.getInstance().retrofit.create(rateNews.class).getRatedNews(
                            AccessToken.getCurrentAccessToken().getUserId(),1);
                case 1:
                    return API.getInstance().retrofit.create(rateNews.class).getRatedNews(
                            AccessToken.getCurrentAccessToken().getUserId(),0);
            }
        }
        return null;
    }


}
