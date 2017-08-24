package me.rishabhkhanna.peopleword.views.Fragments;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import me.rishabhkhanna.peopleword.Adapters.YourNewsViewPagerAdapter;
import me.rishabhkhanna.peopleword.model.Topic;
import me.rishabhkhanna.peopleword.utils.UtilMethods;

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
        View root =  inflater.inflate(me.rishabhkhanna.peopleword.R.layout.fragment_all_news_fragment, container, false);
        viewPager = (ViewPager) root.findViewById(me.rishabhkhanna.peopleword.R.id.vpAllNews);
        tabLayout  = (TabLayout) root.findViewById(me.rishabhkhanna.peopleword.R.id.tabLayoutAllNews);

        ArrayList<Topic> selectedTopicList = UtilMethods.getUserTopics(getContext());

        if(selectedTopicList.size() < 5){
            tabLayout.setTabMode(TabLayout.MODE_FIXED);
        }else {
            tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        }
        viewPagerAdapter = new YourNewsViewPagerAdapter(getChildFragmentManager(),selectedTopicList ,getContext());
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        return root;
    }

}
