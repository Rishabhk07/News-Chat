package me.rishabhkhanna.peopleword.Adapters;

import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;
import android.view.ViewGroup;

import me.rishabhkhanna.peopleword.views.Fragments.AllNewsPageFragment;

import java.util.List;

/**
 * Created by rishabhkhanna on 19/06/17.
 */

public class ViewPagerAdapter extends FragmentPagerAdapter {
    public static final String TAG = "ViewpagerAdapter";
    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
//        pageFragmentList = Arrays.asList(new Fragment[9]);
    }


    @Override
    public Fragment getItem(int position) {
        //get Item is called to instantite the fragment for the given page
        // write login for which fragment to return
        Log.d(TAG, "getItem: new fragment is initialised");
        return AllNewsPageFragment.newInstance(position);

    }

    @Override
    public int getCount() {
        return 15;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
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
            case 9:
                return "TV";
            case 10:
                return "Automotive";
            case 11:
                return "LifeStyle";
            case 12:
                return "Environment";
            case 13:
                return "Good Governance";
            case 14:
                return "Events";
        }
        return super.getPageTitle(position);
    }

}
