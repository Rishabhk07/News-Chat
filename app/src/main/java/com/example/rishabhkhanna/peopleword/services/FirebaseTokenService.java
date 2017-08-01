package com.example.rishabhkhanna.peopleword.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.example.rishabhkhanna.peopleword.Network.API;
import com.example.rishabhkhanna.peopleword.Network.interfaces.getAuth;
import com.example.rishabhkhanna.peopleword.model.AuthResponse;
import com.facebook.AccessToken;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FirebaseTokenService extends FirebaseInstanceIdService{
    public static final String TAG = "Firebase Refresh Token";
    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        String userId = null;
        Log.d(TAG, "onTokenRefresh: FCM" + refreshedToken);
        if(AccessToken.getCurrentAccessToken() != null){
            userId = AccessToken.getCurrentAccessToken().getUserId();
            API.getInstance()
                    .retrofit
                    .create(getAuth.class)
                    .fcmTokenUpdate(refreshedToken, userId)
                    .enqueue(new Callback<AuthResponse>() {
                        @Override
                        public void onResponse(Call<AuthResponse> call, Response<AuthResponse> response) {
                            Log.d(TAG, "onResponse: " + call.request());

                        }
                        @Override
                        public void onFailure(Call<AuthResponse> call, Throwable t) {
                            Log.d(TAG, "onFailure: " + call.request());
                        }
                    });
        }


    }
}
