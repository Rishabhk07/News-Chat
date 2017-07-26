package com.example.rishabhkhanna.peopleword.views.Fragments;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;

import com.example.rishabhkhanna.peopleword.Adapters.YourNewsViewPagerAdapter;
import com.example.rishabhkhanna.peopleword.R;
import com.example.rishabhkhanna.peopleword.utils.UtilMethods;

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
        View root =  inflater.inflate(R.layout.fragment_all_news_fragment, container, false);
        viewPager = (ViewPager) root.findViewById(R.id.vpAllNews);
        tabLayout  = (TabLayout) root.findViewById(R.id.tabLayoutAllNews);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        viewPagerAdapter = new YourNewsViewPagerAdapter(getChildFragmentManager(), UtilMethods.getUserTopics(getContext()),getContext());
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        return root;
    }

}
