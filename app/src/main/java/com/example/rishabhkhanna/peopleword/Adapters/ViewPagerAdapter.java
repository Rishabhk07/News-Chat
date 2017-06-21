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
            case 1:
            newsFragment = PageAllNewsFragment.newInstance(Constants.TOP_URL);
            return newsFragment;
            case 2:
            newsFragment = PageAllNewsFragment.newInstance(Constants.ENTERTAINMENT_URL);
            return newsFragment;
            case 3:
            newsFragment = PageAllNewsFragment.newInstance(Constants.INDIA_URL);
            return newsFragment;
            case 4:
            newsFragment = PageAllNewsFragment.newInstance(Constants.WORLD_URL);
            return newsFragment;
            case 5:
            newsFragment = PageAllNewsFragment.newInstance(Constants.SPORTS_URL);
            return newsFragment;
            case 6:
            newsFragment = PageAllNewsFragment.newInstance(Constants.CRICKET_URL);
            return newsFragment;
            case 7:
            newsFragment = PageAllNewsFragment.newInstance(Constants.BUSINESS_URL);
            return newsFragment;
            case 8:
            newsFragment = PageAllNewsFragment.newInstance(Constants.EDUCATION_URL);
            return newsFragment;

        }
        return null;

    }

    @Override
    public int getCount() {
        return 9;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return "Briefs";
            case 1:
                return "top news";
            case 2:
                return "entertainment";
            case 3:
                return "india";
            case 4:
                return "world";
            case 5:
                return "sports";
            case 6:
                return "cricket";
            case 7:
                return "business";
            case 8:
                return "education";
        }
        return super.getPageTitle(position);
    }
}
