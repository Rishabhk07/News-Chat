package com.example.rishabhkhanna.peopleword.views.Fragments;


import android.os.Bundle;
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
import com.example.rishabhkhanna.peopleword.utils.FetchNews;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class PageAllNewsFragment extends Fragment {

    RecyclerView rvPage;
    ArrayList<ToiJson> newsArrayList = new ArrayList<>();
    public static final String TAG = "PageAllNewsFragment";
    public PageAllNewsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_page_all_news, container, false);

        rvPage = (RecyclerView) root.findViewById(R.id.rvPageAllNews);
        final AllNewsPageRecyclerAdapter allNewsAdapter = new AllNewsPageRecyclerAdapter(newsArrayList,getContext());
        onJsonRecieved onJsonRecieved = new onJsonRecieved() {
            @Override
            public void onSuccess(ArrayList<ToiJson> fetchedNewsList) {
                newsArrayList.clear();
                newsArrayList.addAll(fetchedNewsList);
                allNewsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(VolleyError error) {
                Log.d(TAG, "onError: "  + error);
                Toast.makeText(getActivity(), "Sorry could not fetch news at this moment", Toast.LENGTH_SHORT).show();
            }
        };

        //get Toi data
        FetchNews.getNewsJson(getContext(),onJsonRecieved);
        rvPage.setLayoutManager(new LinearLayoutManager(getContext()));
        rvPage.setAdapter(allNewsAdapter);
        return root;
    }

}
