package com.example.rishabhkhanna.peopleword.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.rishabhkhanna.peopleword.utils.Constants;
import com.example.rishabhkhanna.peopleword.views.Fragments.PageAllNewsFragment;

/**
 * Created by rishabhkhanna on 19/06/17.
 */

public class ViewPagerAdapter extends FragmentPagerAdapter {

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }
    PageAllNewsFragment newsFragment;
    @Override
    public Fragment getItem(int position) {
        //get Item is called to instantite the fragment for the given page
        // write login for which fragment to return
        switch (position) {
          case 0:
            newsFragment = PageAllNewsFragment.newInstance(Constants.BRIEFS_URL);
            return newsFragment;
        }
        return null;

    }

    @Override
    public int getCount() {
        return 1;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return "Briefs";
        }
        return super.getPageTitle(position);
    }
}
