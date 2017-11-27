package me.rishabhkhanna.newschat.views.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import me.rishabhkhanna.newschat.Adapters.YourNewsViewPagerAdapter;
import me.rishabhkhanna.newschat.R;
import me.rishabhkhanna.newschat.model.Topic;
import me.rishabhkhanna.newschat.utils.UtilMethods;
import me.rishabhkhanna.newschat.views.Activities.TopicActivity;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class YourNewsFragment extends Fragment {

    ViewPager viewPager;
    TabLayout tabLayout;
    YourNewsViewPagerAdapter viewPagerAdapter;
    ArrayList<Topic> selectedTopicList;
    public static final int REQ = 777;

    public static final String TAG = "Your News";
    public YourNewsFragment() {
        // Required empty public constructor

    }

    @Override
    public void onAttachFragment(Fragment childFragment) {
        super.onAttachFragment(childFragment);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        selectedTopicList = UtilMethods.getUserTopics(getContext());
        setHasOptionsMenu(true);
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

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.your_news_add_topic, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.addTopic){
//            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
//            Fragment fragment = new NewsTopic();
//            fragmentTransaction.replace(R.id.flActivity_main,fragment).commit();
//            getActivity().setTitle("Topics");
            Intent i = new Intent(getActivity(), TopicActivity.class);
            startActivityForResult(i,REQ);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPause() {
        Log.d(TAG, "onPause: ");
        super.onPause();
    }

    @Override
    public void onStart() {
        Log.d(TAG, "onStart: " + selectedTopicList.size());
        super.onStart();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult: ");
        if(requestCode == REQ){
            selectedTopicList.clear();
            selectedTopicList.addAll(UtilMethods.getUserTopics(getContext()));
            Log.d(TAG, "onActivityResult: " + selectedTopicList.size());
            viewPagerAdapter.notifyDataSetChanged();
        }
    }
}
