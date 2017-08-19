package com.example.rishabhkhanna.peopleword.views.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rishabhkhanna.peopleword.Network.API;
import com.example.rishabhkhanna.peopleword.Network.interfaces.getAuth;
import com.example.rishabhkhanna.peopleword.R;
import com.example.rishabhkhanna.peopleword.model.AuthResponse;
import com.example.rishabhkhanna.peopleword.utils.Constants;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.firebase.iid.FirebaseInstanceId;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginFragment extends Fragment {

    public String intro = "Login to view your personalised news";
    AccessTokenTracker accessTokenTracker;
    public static final String TAG = "LoginFragment";
    CallbackManager callbackManager;
    ProgressBar progressBar;
    LoginButton loginButton;
    public LoginFragment() {
        // Required empty public constructor
    }

    public static LoginFragment newInstance(String param1) {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        args.putString(Constants.LOGIN_STRING,param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            intro = getArguments().getString(Constants.LOGIN_STRING);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view  = inflater.inflate(R.layout.signup_login_layout, container, false);

        callbackManager = CallbackManager.Factory.create();
        FacebookSdk.sdkInitialize(getContext());


        progressBar = (ProgressBar) view.findViewById(R.id.marker_progress);

        loginButton = (LoginButton) view.findViewById(R.id.login_button);
        TextView textView = (TextView) view.findViewById(R.id.tvLogin);
        loginButton.setReadPermissions("email");
        loginButton.setFragment(this);
        textView.setText(intro);

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(final LoginResult loginResult) {
                progressBar.setVisibility(View.VISIBLE);
                loginButton.setVisibility(View.GONE);
                if (Profile.getCurrentProfile() == null) {
                    ProfileTracker profileTracker = new ProfileTracker() {
                        @Override
                        protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {
                            Profile.setCurrentProfile(currentProfile);
                            this.stopTracking();
                        }
                    };
                } else {
                    Log.d(TAG, "onSuccess: FirstName" + Profile.getCurrentProfile().getFirstName());
                }

                saveLoginInfo(loginResult);
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
//


        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
                if (currentAccessToken != null) {
                    AccessToken.setCurrentAccessToken(currentAccessToken);
                    API.getInstance().retrofit
                            .create(getAuth.class)
                            .facebookTokenUpdate(currentAccessToken.getToken(), currentAccessToken.getUserId())
                            .enqueue(new Callback<AuthResponse>() {
                                @Override
                                public void onResponse(Call<AuthResponse> call, Response<AuthResponse> response) {
                                    Log.d(TAG, "onResponse: " + response.body());
                                    Log.d(TAG, "onResponse: " + response.body().getUser().getName());
                                }

                                @Override
                                public void onFailure(Call<AuthResponse> call, Throwable t) {

                                }
                            });
                }
            }
        };



        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult: request code" + requestCode);
        Log.d(TAG, "onActivityResult: result code" + resultCode);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onDestroy() {
        if (accessTokenTracker != null) {
            accessTokenTracker.stopTracking();
        }
        super.onDestroy();
    }

    public void saveLoginInfo(final LoginResult loginResult){
        String fcm_token = FirebaseInstanceId.getInstance().getToken();
        API.getInstance()
                .retrofit
                .create(getAuth.class)
                .facebookUserAuth(
                        loginResult.getAccessToken().getToken(),
                        loginResult.getAccessToken().getUserId(),
                        fcm_token
                ).enqueue(new Callback<AuthResponse>() {
            @Override
            public void onResponse(retrofit2.Call<AuthResponse> call, Response<AuthResponse> response) {

                Log.d(TAG, "onResponse: " + response);
                Log.d(TAG, "onResponse: " + response.body().getUser().getName());
                Log.d(TAG, "onResponse: " + response.body().getUser().getEmail());

                Log.d(TAG, "onResponse: " + call.request());
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Constants.AUTH_DETAILS, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(Constants.LOGIN_TOKEN, loginResult.getAccessToken().getToken());
                editor.putString(Constants.LOGIN_USER_ID, loginResult.getAccessToken().getUserId());
                editor.putString(Constants.AUTH_EMAIL,response.body().getUser().getEmail());
                editor.apply();
                progressBar.setVisibility(View.GONE);
                getActivity().recreate();
            }

            @Override
            public void onFailure(Call<AuthResponse> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Log.d(TAG, "onFailure: " + call.request());
                Log.d(TAG, "onFailure: " + t.getMessage());
                loginButton.setVisibility(View.VISIBLE);
                LoginManager.getInstance().logOut();
                Toast.makeText(getActivity(), "Cannot Login Right Now", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
