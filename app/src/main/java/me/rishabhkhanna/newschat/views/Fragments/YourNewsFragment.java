package me.rishabhkhanna.newschat.views.Fragments;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import me.rishabhkhanna.newschat.Adapters.YourNewsViewPagerAdapter;
import me.rishabhkhanna.newschat.R;
import me.rishabhkhanna.newschat.model.Topic;
import me.rishabhkhanna.newschat.utils.UtilMethods;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class YourNewsFragment extends Fragment {

    ViewPager viewPager;
    TabLayout tabLayout;
    YourNewsViewPagerAdapter viewPagerAdapter;
    public YourNewsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ArrayList<Topic> selectedTopicList = UtilMethods.getUserTopics(getContext());
        if(selectedTopicList.size() != 0) {
            View root = inflater.inflate(me.rishabhkhanna.newschat.R.layout.fragment_all_news_fragment, container, false);
            viewPager = (ViewPager) root.findViewById(me.rishabhkhanna.newschat.R.id.vpAllNews);
            tabLayout = (TabLayout) root.findViewById(me.rishabhkhanna.newschat.R.id.tabLayoutAllNews);


            if (selectedTopicList.size() < 5) {
                tabLayout.setTabMode(TabLayout.MODE_FIXED);
            } else {
                tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
            }
            viewPagerAdapter = new YourNewsViewPagerAdapter(getChildFragmentManager(), selectedTopicList, getContext());
            viewPager.setAdapter(viewPagerAdapter);
            tabLayout.setupWithViewPager(viewPager);

            return root;
        }else{
            View root = inflater.inflate(R.layout.your_news_page_intro,container,false);
            return root;
        }
    }

}
