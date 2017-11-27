package me.rishabhkhanna.newschat.Adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import me.rishabhkhanna.newschat.model.Topic;
import me.rishabhkhanna.newschat.views.Fragments.YourNewsPageFragment;

import java.util.ArrayList;

/**
 * Created by rishabhkhanna on 26/07/17.
 */

public class YourNewsViewPagerAdapter extends FragmentPagerAdapter {
    ArrayList<Topic> userTopics;
    Context context;
    YourNewsPageFragment newsFragment;

    public YourNewsViewPagerAdapter(FragmentManager fm, ArrayList<Topic> userTopics, Context context) {
        super(fm);
        this.userTopics = userTopics;
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        newsFragment = YourNewsPageFragment.newInstance(userTopics,position);
        return newsFragment;
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public int getCount() {
        return userTopics.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return userTopics.get(position).getName();
    }
}
