package com.example.rishabhkhanna.peopleword.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.rishabhkhanna.peopleword.views.Fragments.ProfileNewsFragment;

/**
 * Created by rishabhkhanna on 13/08/17.
 */

public class ProfileViewPagerAdapter extends FragmentPagerAdapter {

    public ProfileViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
         ProfileNewsFragment profileNewsFragment = ProfileNewsFragment.getInstance(position);
        return profileNewsFragment;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return "Likes";
            case 1:
                return "Dislikes";
            case 2:
                return "Chats";
        }
        return super.getPageTitle(position);
    }
}