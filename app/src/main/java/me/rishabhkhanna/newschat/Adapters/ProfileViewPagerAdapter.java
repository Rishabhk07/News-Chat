package me.rishabhkhanna.newschat.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import me.rishabhkhanna.newschat.views.Fragments.ProfileNewsFragment;

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
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Likes";
            case 1:
                return "Dislikes";
        }
        return super.getPageTitle(position);
    }
}
