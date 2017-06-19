package com.example.rishabhkhanna.peopleword.views.Fragments;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rishabhkhanna.peopleword.Adapters.ViewPagerAdapter;
import com.example.rishabhkhanna.peopleword.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AllNewsFragment extends Fragment {

    RecyclerView rvAllNews;
    ViewPager allNewsViewPager;
    TabLayout tabLayoutAllNews;
    ViewPagerAdapter viewPagerAdapter;
    public AllNewsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_all_news_fragment, container, false);
        allNewsViewPager = (ViewPager) root.findViewById(R.id.vpAllNews);
        tabLayoutAllNews = (TabLayout) root.findViewById(R.id.tabLayoutAllNews);

        viewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager());
        allNewsViewPager.setAdapter(viewPagerAdapter);

        tabLayoutAllNews.setupWithViewPager(allNewsViewPager);
            return root;
    }

}
