package com.example.rishabhkhanna.peopleword.views.Fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.rishabhkhanna.peopleword.Adapters.AllNewsAdapter;
import com.example.rishabhkhanna.peopleword.Adapters.AllNewsPageRecyclerAdapter;
import com.example.rishabhkhanna.peopleword.Interfaces.onJsonRecieved;
import com.example.rishabhkhanna.peopleword.R;
import com.example.rishabhkhanna.peopleword.model.ToiJson;
import com.example.rishabhkhanna.peopleword.utils.Constants;
import com.example.rishabhkhanna.peopleword.utils.EndlessRecyclerViewScrollListener;
import com.example.rishabhkhanna.peopleword.utils.FetchNews;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class PageAllNewsFragment extends Fragment {

    RecyclerView rvPage;
    ArrayList<ToiJson> newsArrayList = new ArrayList<>();
    String url;
    int counter;
    EndlessRecyclerViewScrollListener scrollListener;
    public static final String TAG = "PageAllNewsFragment";
    public PageAllNewsFragment() {
        // Required empty public constructor
    }

    public static PageAllNewsFragment newInstance(String url){
        PageAllNewsFragment newsFragment = new PageAllNewsFragment();
        Bundle args = new Bundle();
        args.putString(Constants.fragment_key,url);
        newsFragment.setArguments(args);
        return newsFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null){
            url = getArguments().getString(Constants.fragment_key);
            counter = 0;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_page_all_news, container, false);

        rvPage = (RecyclerView) root.findViewById(R.id.rvPageAllNews);
        final AllNewsPageRecyclerAdapter allNewsAdapter = new AllNewsPageRecyclerAdapter(newsArrayList,getContext());
        String urlFirstPage = url + 0;
        final onJsonRecieved onJsonRecieved = new onJsonRecieved() {
            @Override
            public void onSuccess(ArrayList<ToiJson> fetchedNewsList) {
                newsArrayList.addAll(fetchedNewsList);
                allNewsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(VolleyError error) {
                Log.d(TAG, "onError: "  + error);
                Toast.makeText(getContext(), "Sorry could not fetch news at this moment", Toast.LENGTH_SHORT).show();
            }
        };
        String s = new String();

        //get Toi data
        FetchNews.getUrlNews(getContext(),onJsonRecieved,urlFirstPage);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rvPage.setLayoutManager(linearLayoutManager);
        rvPage.setAdapter(allNewsAdapter);


        scrollListener = new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                counter++;
                Log.d(TAG, "onLoadMore: page: " + page + "Total Items Count "+totalItemsCount + "counter: " + counter);
                String nextPageUrl = url + counter;
                FetchNews.getUrlNews(getContext(),onJsonRecieved,nextPageUrl);
            }
        };

        rvPage.addOnScrollListener(scrollListener);

        return root;
    }

}
