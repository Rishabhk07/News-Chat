package com.example.rishabhkhanna.peopleword.views.Fragments;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.telecom.Call;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.daprlabs.cardstack.SwipeDeck;
import com.example.rishabhkhanna.peopleword.Adapters.RateNewsAdapter;
import com.example.rishabhkhanna.peopleword.Interfaces.onJsonRecieved;
import com.example.rishabhkhanna.peopleword.Network.API;
import com.example.rishabhkhanna.peopleword.Network.interfaces.getAuth;
import com.example.rishabhkhanna.peopleword.R;
import com.example.rishabhkhanna.peopleword.model.NewsJson;
import com.example.rishabhkhanna.peopleword.model.NewsJson;
import com.example.rishabhkhanna.peopleword.model.User;
import com.example.rishabhkhanna.peopleword.utils.Constants;
import com.example.rishabhkhanna.peopleword.utils.FetchNews;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.gson.Gson;

import java.util.ArrayList;

import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class RateNewFragment extends Fragment {

    private SwipeDeck swipeDeck;
    public RateNewsAdapter.SwipeCardAdapter swipeCardAdapter;
    Button dislikeBtn, likeBtn;
    ArrayList<NewsJson> newsArrayList = new ArrayList<>();
    CallbackManager callbackManager;
    SharedPreferences sharedPreferences;
    public static final String TAG = "RateNewsActivity";

    public RateNewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        sharedPreferences = getActivity().getSharedPreferences("newsapp", Context.MODE_PRIVATE);

        if ((sharedPreferences.getString(Constants.LOGIN_TOKEN, "null")).equals("null")) {
            return getSignupPage(inflater, container);
        }

        View root = inflater.inflate(R.layout.fragment_rate_new, container, false);
        swipeDeck = (SwipeDeck) root.findViewById(R.id.swipe_deck);
        likeBtn = (Button) root.findViewById(R.id.like_btn);
        dislikeBtn = (Button) root.findViewById(R.id.dislike_btn);

        swipeCardAdapter = RateNewsAdapter.getSwipeCardAdapter(newsArrayList, getContext());
        swipeDeck.setAdapter(swipeCardAdapter);
        //Interface callback to show data after download
        onJsonRecieved onJsonRecieved = new onJsonRecieved() {
            @Override
            public void onSuccess(ArrayList<NewsJson> fetchedNewsList) {
                newsArrayList.addAll(fetchedNewsList);
                swipeCardAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(VolleyError error) {
                Log.d(TAG, "onError: " + error);
                Toast.makeText(getActivity(), "Sorry could not fetch news at this moment", Toast.LENGTH_SHORT).show();
            }
        };

        //get Toi data
        FetchNews.getNewsJson(getActivity(), onJsonRecieved);

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

    private View getSignupPage(LayoutInflater inflater, ViewGroup container) {

        View signup_root = inflater.inflate(R.layout.signup_login_layout, container, false);
        final ProgressBar progressBar = (ProgressBar) signup_root.findViewById(R.id.marker_progress);
        final LoginButton loginButton = (LoginButton) signup_root.findViewById(R.id.login_button);
        loginButton.setReadPermissions("email");
        loginButton.setFragment(this);
        callbackManager = CallbackManager.Factory.create();
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(final LoginResult loginResult) {
                progressBar.setVisibility(View.VISIBLE);
                loginButton.setVisibility(View.GONE);
                Log.d(TAG, "onSuccess: accessToken" + loginResult.getAccessToken());
                Log.d(TAG, "onSuccess: permissions" + loginResult.getRecentlyGrantedPermissions());
                Log.d(TAG, "onSuccess: token" + loginResult.getAccessToken().getToken());
                Log.d(TAG, "onSuccess: application Id" + loginResult.getAccessToken().getApplicationId());
                Log.d(TAG, "onSuccess: user_id" + loginResult.getAccessToken().getUserId());
                Log.d(TAG, "onSuccess: isExpired" + loginResult.getAccessToken().isExpired());
                Log.d(TAG, "onSuccess: FirstName" + Profile.getCurrentProfile().getFirstName());


                API.getInstance()
                        .retrofit
                        .create(getAuth.class)
                        .userAuth(
                                Profile.getCurrentProfile().getFirstName(),
                                Profile.getCurrentProfile().getLastName(),
                                loginResult.getAccessToken().getToken(),
                                loginResult.getAccessToken().getUserId()
                        ).enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(retrofit2.Call<User> call, Response<User> response) {

                        Log.d(TAG, "onResponse: " + response.body());
                        Log.d(TAG, "onResponse: " + call.request());
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString(Constants.LOGIN_TOKEN,loginResult.getAccessToken().getToken());
                        editor.putString(Constants.LOGIN_USER_ID,loginResult.getAccessToken().getUserId());
                        editor.apply();
                        progressBar.setVisibility(View.GONE);
                        getActivity().recreate();
                    }

                    @Override
                    public void onFailure(retrofit2.Call<User> call, Throwable t) {
                        progressBar.setVisibility(View.GONE);
                        Log.d(TAG, "onFailure: " + call.request());
                        Log.d(TAG, "onFailure: " + t.getMessage());
                        loginButton.setVisibility(View.VISIBLE);
                    }
                });

            }

            @Override
            public void onCancel() {
                Log.d(TAG, "onCancel: Cancel");
            }

            @Override
            public void onError(FacebookException error) {
                Log.d(TAG, "onError: " + error.getMessage());
            }
        });
        return signup_root;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult: request code" + requestCode);
        Log.d(TAG, "onActivityResult: result code" + resultCode);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
