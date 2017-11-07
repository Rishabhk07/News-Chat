package me.rishabhkhanna.newschat.views.Fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import me.rishabhkhanna.newschat.Adapters.ViewPagerAdapter;
import me.rishabhkhanna.newschat.utils.UtilMethods;

/**
 * A simple {@link Fragment} subclass.
 */
public class AllNewsFragment extends Fragment {

    RecyclerView rvAllNews;
    ViewPager allNewsViewPager;
    TabLayout tabLayoutAllNews;
    ViewPagerAdapter viewPagerAdapter;
    int goToTab = -1;
    public AllNewsFragment() {
        // Required empty public constructor
    }

    public static AllNewsFragment getInstance(String tabName){
        AllNewsFragment newsFragment  = new AllNewsFragment();
        Bundle bundle = new Bundle();
        bundle.putString("tab_name",tabName);
        newsFragment.setArguments(bundle);
        return newsFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        if(getArguments() != null){
            String tabName = getArguments().getString("tab_name");
            goToTab = UtilMethods.getPageFromTopic(tabName);
        }
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(me.rishabhkhanna.newschat.R.layout.fragment_all_news_fragment, container, false);

        allNewsViewPager = (ViewPager) root.findViewById(me.rishabhkhanna.newschat.R.id.vpAllNews);
        tabLayoutAllNews = (TabLayout) root.findViewById(me.rishabhkhanna.newschat.R.id.tabLayoutAllNews);
        tabLayoutAllNews.setTabMode(TabLayout.MODE_SCROLLABLE);
        viewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager());
        allNewsViewPager.setAdapter(viewPagerAdapter);
        tabLayoutAllNews.setupWithViewPager(allNewsViewPager);
        if(goToTab != -1){
            allNewsViewPager.setCurrentItem(goToTab);
        }
        return root;
    }

}
