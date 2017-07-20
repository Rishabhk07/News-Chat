package com.example.rishabhkhanna.peopleword.views.Fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.rishabhkhanna.peopleword.Adapters.AllNewsPageRecyclerAdapter;
import com.example.rishabhkhanna.peopleword.Interfaces.onJsonRecieved;
import com.example.rishabhkhanna.peopleword.Network.NewsAPI;
import com.example.rishabhkhanna.peopleword.R;
import com.example.rishabhkhanna.peopleword.model.NewsJson;
import com.example.rishabhkhanna.peopleword.utils.Constants;
import com.example.rishabhkhanna.peopleword.utils.EndlessRecyclerViewScrollListener;
import com.example.rishabhkhanna.peopleword.utils.FetchNews;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class PageAllNewsFragment extends Fragment {

    RecyclerView rvPage;
    SwipeRefreshLayout swipeRefreshLayout;
    ArrayList<NewsJson> newsArrayList = new ArrayList<>();
    String url;
    int counter;
    boolean fetchFristApi = true;
    EndlessRecyclerViewScrollListener scrollListener;
    Call<ArrayList<NewsJson>> call;
    int position;
    public static final String TAG = "PageAllNewsFragment";

    public PageAllNewsFragment() {
        // Required empty public constructor
    }

    public static PageAllNewsFragment newInstance(int position) {
        PageAllNewsFragment newsFragment = new PageAllNewsFragment();
        Bundle args = new Bundle();
//        args.putString(Constants.fragment_key,url);
        args.putInt(Constants.fragment_key, position);
        newsFragment.setArguments(args);
        return newsFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: Create Here");
        if (getArguments() != null) {
//            url = getArguments().getString(Constants.fragment_key);

            position = getArguments().getInt(Constants.fragment_key);
            counter = 0;
            setupCall(position,counter);
            fetchFristApi = true;
        }

    }


    private Call<ArrayList<NewsJson>> setupCall(int position, int counter) {
        switch (position) {
            case 0:
                call = NewsAPI.getInstance().getNews.getBriefs(String.valueOf(counter));
                break;
            case 1:
                call = NewsAPI.getInstance().getNews.getTopNews(String.valueOf(counter));
                break;
            case 2:
                call = NewsAPI.getInstance().getNews.getEntertainment(String.valueOf(counter));
                break;
            case 3:
                call = NewsAPI.getInstance().getNews.getIndiaNews(String.valueOf(counter));
                break;
            case 4:
                call = NewsAPI.getInstance().getNews.getWorldNews(String.valueOf(counter));
                break;
            case 5:
                call = NewsAPI.getInstance().getNews.getSports(String.valueOf(counter));
                break;
            case 6:
                call = NewsAPI.getInstance().getNews.getCricketNews(String.valueOf(counter));
                break;
            case 7:
                call = NewsAPI.getInstance().getNews.getBusinessNews(String.valueOf(counter));
                break;
            case 8:
                call = NewsAPI.getInstance().getNews.getEducatoinNews(String.valueOf(counter));
                break;
            case 9:
                call = NewsAPI.getInstance().getNews.getTVNews(String.valueOf(counter));
                break;
            case 10:
                call = NewsAPI.getInstance().getNews.getAuomotiveNews(String.valueOf(counter));
                break;
            case 11:
                call = NewsAPI.getInstance().getNews.getLifestyleNews(String.valueOf(counter));
                break;
            case 12:
                call = NewsAPI.getInstance().getNews.getEnvironmentNews(String.valueOf(counter));
                break;
            case 13:
                call = NewsAPI.getInstance().getNews.getGoodGovNews(String.valueOf(counter));
                break;
            case 14:
                call = NewsAPI.getInstance().getNews.getEvents(String.valueOf(counter));
                break;
        }

             return call;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_page_all_news, container, false);
        Log.d(TAG, "onCreateView: ");
        rvPage = (RecyclerView) root.findViewById(R.id.rvPageAllNews);
        final AllNewsPageRecyclerAdapter allNewsAdapter = new AllNewsPageRecyclerAdapter(newsArrayList, getContext());
        String urlFirstPage = url + 0;
        final onJsonRecieved onJsonRecieved = new onJsonRecieved() {
            @Override
            public void onSuccess(ArrayList<NewsJson> fetchedNewsList) {
                newsArrayList.addAll(fetchedNewsList);
                allNewsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(VolleyError error) {
                Log.d(TAG, "onError: " + error);
                Toast.makeText(getContext(), "Sorry could not fetch news at this moment", Toast.LENGTH_SHORT).show();
            }
        };

        //get Toi data
        if (counter == 0 && fetchFristApi) {
//            FetchNews.getUrlNews(getContext(), onJsonRecieved, urlFirstPage);
            call.enqueue(new Callback<ArrayList<NewsJson>>() {
                @Override
                public void onResponse(Call<ArrayList<NewsJson>> call, Response<ArrayList<NewsJson>> response) {
                    newsArrayList.addAll(response.body());
                    allNewsAdapter.notifyDataSetChanged();
                }

                @Override
                public void onFailure(Call<ArrayList<NewsJson>> call, Throwable t) {
                    Log.d(TAG, "onError: " + t.getMessage());
                    Toast.makeText(getContext(), "Sorry could not fetch news at this moment", Toast.LENGTH_SHORT).show();
                }
            });
            fetchFristApi = false;
        }
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rvPage.setLayoutManager(linearLayoutManager);
        rvPage.setAdapter(allNewsAdapter);


        scrollListener = new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                counter++;
                Log.d(TAG, "onLoadMore: page: " + page + "Total Items Count " + totalItemsCount + "counter: " + counter);
                String nextPageUrl = url + counter;
//                FetchNews.getUrlNews(getContext(), onJsonRecieved, nextPageUrl);
                setupCall(position,counter).enqueue(new Callback<ArrayList<NewsJson>>() {
                    @Override
                    public void onResponse(Call<ArrayList<NewsJson>> call, Response<ArrayList<NewsJson>> response) {
                        newsArrayList.addAll(response.body());
                        allNewsAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(Call<ArrayList<NewsJson>> call, Throwable t) {

                    }
                });
            }
        };
        rvPage.addOnScrollListener(scrollListener);

        return root;
    }





}
