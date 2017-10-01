package me.rishabhkhanna.peopleword.views.Activities;

import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import me.rishabhkhanna.peopleword.Network.API;
import me.rishabhkhanna.peopleword.Network.interfaces.getAuth;
import me.rishabhkhanna.peopleword.model.AuthResponse;
import com.facebook.AccessToken;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SettingsActivity extends PreferenceActivity{
    public static final String TAG = "Setting Activity";
    CheckBoxPreference briefNotification;
    CheckBoxPreference topicNotification;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(me.rishabhkhanna.peopleword.R.xml.prefrences);
        setTitle("Settings");

        briefNotification = (CheckBoxPreference) findPreference("notification");

        topicNotification = (CheckBoxPreference) findPreference("topic_notification");

        briefNotification.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                if(briefNotification.isChecked()){
                    topicNotification.setChecked(false);
                    setSettingsToServer(true);
                }else {
                    if(!topicNotification.isChecked()){
                        briefNotification.setChecked(true);
                    }
                }
                return false;
            }
        });

        topicNotification.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                if(topicNotification.isChecked()){
                    briefNotification.setChecked(false);
                    setSettingsToServer(false);
                }else{
                    if(!briefNotification.isChecked()){
                        topicNotification.setChecked(true);
                    }
                }
                return false;
            }
        });

    }

    public void setSettingsToServer(final Boolean notification){
//        Log.d(TAG, "setSettingsToServer: ");
        if(AccessToken.getCurrentAccessToken() != null){
            API.getInstance().retrofit.create(getAuth.class).updateNotification(
                    AccessToken.getCurrentAccessToken().getUserId(),
                    notification).enqueue(new Callback<AuthResponse>() {
                @Override
                public void onResponse(Call<AuthResponse> call, Response<AuthResponse> response) {
                    if(response.body().getSuccess()){
                        Toast.makeText(SettingsActivity.this, "Setting Updated", Toast.LENGTH_SHORT).show();
                    }else if(response.body().getSuccess()){
                        Toast.makeText(SettingsActivity.this, "Cannot update setting", Toast.LENGTH_SHORT).show();
                        if(notification){
                            topicNotification.setChecked(true);
                        }else{
                            briefNotification.setChecked(true);
                        }
                    }
                }

                @Override
                public void onFailure(Call<AuthResponse> call, Throwable t) {

                }
            });
        }
    }


}
