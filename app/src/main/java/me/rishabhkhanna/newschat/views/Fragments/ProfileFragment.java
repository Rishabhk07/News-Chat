package me.rishabhkhanna.peopleword.views.Fragments;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import me.rishabhkhanna.peopleword.Adapters.ProfileViewPagerAdapter;
import me.rishabhkhanna.peopleword.utils.Constants;
import me.rishabhkhanna.peopleword.views.Activities.SettingsActivity;

import com.facebook.Profile;
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {


    public ProfileFragment() {
        // Required empty public constructor
    }

    ImageView imProfileImage;
    TextView tvName;
    TextView tvEmail;
    ViewPager viewPager;
    TabLayout tabLayout;
    ImageView settingImageView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(me.rishabhkhanna.peopleword.R.layout.profile_layout, container, false);
        imProfileImage = (ImageView) view.findViewById(me.rishabhkhanna.peopleword.R.id.imProfile);
        tvName = (TextView) view.findViewById(me.rishabhkhanna.peopleword.R.id.tvProfileName);
        tvEmail = (TextView) view.findViewById(me.rishabhkhanna.peopleword.R.id.tvProfileEmail);
        settingImageView = (ImageView) view.findViewById(me.rishabhkhanna.peopleword.R.id.imageViewSetting);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Constants.AUTH_DETAILS, Context.MODE_PRIVATE);
        if(Profile.getCurrentProfile() != null){
            Picasso.with(getContext()).load(Profile.getCurrentProfile().getProfilePictureUri(300,300)).into(imProfileImage);
            tvName.setText(Profile.getCurrentProfile().getName());

            tvEmail.setText(sharedPreferences.getString(Constants.AUTH_EMAIL,""));
        }
        viewPager = (ViewPager) view.findViewById(me.rishabhkhanna.peopleword.R.id.profileViewpager);
        tabLayout = (TabLayout) view.findViewById(me.rishabhkhanna.peopleword.R.id.profileTablayout);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        ProfileViewPagerAdapter profileViewPagerAdapter = new ProfileViewPagerAdapter(getChildFragmentManager());
        viewPager.setAdapter(profileViewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        settingImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), SettingsActivity.class);
                startActivity(i);
            }
        });
        return view;
    }

}
