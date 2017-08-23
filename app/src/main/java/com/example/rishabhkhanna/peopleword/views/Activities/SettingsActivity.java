package com.example.rishabhkhanna.peopleword.views.Activities;

import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;

import android.os.Bundle;
import android.util.Log;

import com.example.rishabhkhanna.peopleword.R;

import java.io.ObjectStreamException;

public class SettingsActivity extends PreferenceActivity{
    public static final String TAG = "Setting Activity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.prefrences);
        setTitle("Settings");
        final CheckBoxPreference notification = (CheckBoxPreference) findPreference("notification");
        final CheckBoxPreference topicNotification = (CheckBoxPreference) findPreference("topic_notification");
//        notification.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
//            @Override
//            public boolean onPreferenceChange(Preference preference, Object newValue) {
//                Log.d(TAG, "onPreferenceChange: " + newValue);
//                if((Boolean)newValue) {
//                    topicNotification.setChecked(false);
//                }else {
//                    Log.d(TAG, "onPreferenceChange: TOPIC IN NOTI" + topicNotification.isChecked());
//                    if(!topicNotification.isChecked()){
//                        notification.setChecked(true);
//                        newValue = true;
//                    }
//                }
//                return true;
//            }
//        });

        notification.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                if(notification.isChecked()){
                    topicNotification.setChecked(false);
                }else {
                    if(!topicNotification.isChecked()){
                        notification.setChecked(true);
                    }
                }
                return false;
            }
        });

        topicNotification.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                if(topicNotification.isChecked()){
                    notification.setChecked(false);
                }else{
                    if(!notification.isChecked()){
                        topicNotification.setChecked(true);
                    }
                }
                return false;
            }
        });
//        topicNotification.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
//            @Override
//            public boolean onPreferenceChange(Preference preference, Object newValue) {
//                Log.d(TAG, "onPreferenceChange: TOPIC" + newValue);
//                if((Boolean)newValue){
//                    notification.setChecked(false);
//                }else {
//                    if(!notification.isChecked()){
//                        topicNotification.setChecked(true);
//                        newValue = true;
//                    }
//                }
//                return true;
//            }
//        });

    }


}
