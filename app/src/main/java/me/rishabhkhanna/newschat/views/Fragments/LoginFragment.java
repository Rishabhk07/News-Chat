package me.rishabhkhanna.newschat.views.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import me.rishabhkhanna.newschat.Network.API;
import me.rishabhkhanna.newschat.Network.interfaces.getAuth;
import me.rishabhkhanna.newschat.model.AuthResponse;
import me.rishabhkhanna.newschat.utils.Constants;
import me.rishabhkhanna.newschat.views.Activities.BaseActivity;

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

import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginFragment extends Fragment {

    public String intro = "Login to view your personalised news";
    int fragmentPage = 0;
    AccessTokenTracker accessTokenTracker;
    ProfileTracker profileTracker;
    public static final String TAG = "LoginFragment";
    CallbackManager callbackManager;
    ProgressBar progressBar;
    LoginButton loginButton;
    public LoginFragment() {
        // Required empty public constructor
    }

    public static LoginFragment newInstance(String param1, int fragmentPage) {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        args.putString(Constants.LOGIN_STRING,param1);
        args.putInt(Constants.loginPage,fragmentPage);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            intro = getArguments().getString(Constants.LOGIN_STRING);
            fragmentPage = getArguments().getInt(Constants.loginPage);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view  = inflater.inflate(me.rishabhkhanna.newschat.R.layout.signup_login_layout, container, false);

        callbackManager = CallbackManager.Factory.create();
        FacebookSdk.sdkInitialize(getContext());


        progressBar = (ProgressBar) view.findViewById(me.rishabhkhanna.newschat.R.id.marker_progress);

        loginButton = (LoginButton) view.findViewById(me.rishabhkhanna.newschat.R.id.login_button);
        TextView textView = (TextView) view.findViewById(me.rishabhkhanna.newschat.R.id.tvLogin);
        loginButton.setReadPermissions(Arrays.asList("email","public_profile"));
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
//                    Log.d(TAG, "onSuccess: FirstName" + Profile.getCurrentProfile().getFirstName());
                }

                saveLoginInfo(loginResult);
            }

            @Override
            public void onCancel() {
                Log.d(TAG, "onCancel: Cancel");
            }

            @Override
            public void onError(FacebookException error) {
//                Log.d(TAG, "onError: " + error.getMessage());
            }
        });
//


        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, final AccessToken currentAccessToken) {
                if (currentAccessToken != null) {
                    AccessToken.setCurrentAccessToken(currentAccessToken);
                    API.getInstance().retrofit
                            .create(getAuth.class)
                            .facebookTokenUpdate(currentAccessToken.getToken(), currentAccessToken.getUserId())
                            .enqueue(new Callback<AuthResponse>() {
                                @Override
                                public void onResponse(Call<AuthResponse> call, Response<AuthResponse> response) {


                                }

                                @Override
                                public void onFailure(Call<AuthResponse> call, Throwable t) {

                                }
                            });
                }

            }
        };

//        Log.d(TAG, "onCurrentAccessTokenChanged: Profile Token");

        profileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {
                if (currentProfile != null) {
                    Log.d(TAG, "onCurrentProfileChanged: ");
                    Profile.setCurrentProfile(currentProfile);
                }
            }
        } ;
        profileTracker.startTracking();



        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        Log.d(TAG, "onActivityResult: request code" + requestCode);
//        Log.d(TAG, "onActivityResult: result code" + resultCode);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onDestroy() {
        if (accessTokenTracker != null) {
            accessTokenTracker.stopTracking();
        }
        if(profileTracker != null){
            profileTracker.stopTracking();
        }
        super.onDestroy();
    }

    public void saveLoginInfo(final LoginResult loginResult){
        String fcm_token = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "saveLoginInfo: TOKEN: " + fcm_token);
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

//                Log.d(TAG, "onResponse: " + response);
//                Log.d(TAG, "onResponse: " + response.body().getUser().getName());
//                Log.d(TAG, "onResponse: " + response.body().getUser().getEmail());

                Log.d(TAG, "onResponse: " + call.request());
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Constants.AUTH_DETAILS, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(Constants.LOGIN_TOKEN, loginResult.getAccessToken().getToken());
                editor.putString(Constants.LOGIN_USER_ID, loginResult.getAccessToken().getUserId());
                editor.putString(Constants.AUTH_EMAIL,response.body().getUser().getEmail());
                editor.apply();
                progressBar.setVisibility(View.GONE);
//                getActivity().recreate();
                if(fragmentPage == 5){
                    getActivity().recreate();
                }else {
                    Intent intent = new Intent(getContext(), BaseActivity.class).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    intent.putExtra(Constants.loginFragmentIntent,fragmentPage);
                    startActivity(intent);
                }



            }

            @Override
            public void onFailure(Call<AuthResponse> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
//                Log.d(TAG, "onFailure: " + call.request());
//                Log.d(TAG, "onFailure: " + t.getMessage());
                loginButton.setVisibility(View.VISIBLE);
                LoginManager.getInstance().logOut();
                Toast.makeText(getActivity(), "Cannot Login Right Now", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
