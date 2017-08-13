package com.example.rishabhkhanna.peopleword.views.Fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rishabhkhanna.peopleword.Adapters.ProfileViewPagerAdapter;
import com.example.rishabhkhanna.peopleword.R;
import com.example.rishabhkhanna.peopleword.utils.Constants;
import com.facebook.AccessToken;
import com.facebook.Profile;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

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
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile_layout, container, false);
        imProfileImage = (ImageView) view.findViewById(R.id.imProfile);
        tvName = (TextView) view.findViewById(R.id.tvProfileName);
        tvEmail = (TextView) view.findViewById(R.id.tvProfileEmail);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Constants.LOGIN_TOKEN, Context.MODE_PRIVATE);
        if(Profile.getCurrentProfile() != null){
            Picasso.with(getContext()).load(Profile.getCurrentProfile().getProfilePictureUri(300,300)).into(imProfileImage);
            tvName.setText(Profile.getCurrentProfile().getName());
            sharedPreferences.getString(Constants.AUTH_EMAIL,"");
        }
        viewPager = (ViewPager) view.findViewById(R.id.profileViewpager);
        tabLayout = (TabLayout) view.findViewById(R.id.profileTablayout);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        ProfileViewPagerAdapter profileViewPagerAdapter = new ProfileViewPagerAdapter(getChildFragmentManager());
        viewPager.setAdapter(profileViewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        return view;
    }

}
