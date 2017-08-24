package me.rishabhkhanna.peopleword.views.Fragments;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import me.rishabhkhanna.peopleword.Adapters.AllNewsPageRecyclerAdapter;
import me.rishabhkhanna.peopleword.Network.NewsAPI;
import me.rishabhkhanna.peopleword.model.AuthDetails;
import me.rishabhkhanna.peopleword.model.NewsJson;
import me.rishabhkhanna.peopleword.utils.Constants;
import me.rishabhkhanna.peopleword.utils.EndlessRecyclerViewScrollListener;
import me.rishabhkhanna.peopleword.utils.UtilMethods;
import com.facebook.AccessToken;


import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class AllNewsPageFragment extends Fragment {

    RecyclerView rvPage;
    SwipeRefreshLayout swipeRefreshLayout;
    ProgressBar progressBar;
    ArrayList<NewsJson> newsArrayList = new ArrayList<>();
    String url;
    int counter;
    boolean fetchFristApi = true;
    EndlessRecyclerViewScrollListener scrollListener;
    Call<ArrayList<NewsJson>> call;
    String currentPage = null;
    int position;
    public static final String TAG = "AllNewsPageFragment";
    AuthDetails authDetails;
    FrameLayout frameLayout;
    public AllNewsPageFragment() {
        // Required empty public constructor
    }

    public static AllNewsPageFragment newInstance(int position) {
        AllNewsPageFragment newsFragment = new AllNewsPageFragment();
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
            if(AccessToken.getCurrentAccessToken() != null){
                authDetails = new AuthDetails(AccessToken.getCurrentAccessToken().getToken(),AccessToken.getCurrentAccessToken().getUserId());
            }else{
                authDetails = new AuthDetails("null","null");
            }
//            setupCall(position, counter);
            fetchFristApi = true;
        }

    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle("All News");
        View root = inflater.inflate(me.rishabhkhanna.peopleword.R.layout.fragment_page_all_news, container, false);

        swipeRefreshLayout = (SwipeRefreshLayout) root.findViewById(me.rishabhkhanna.peopleword.R.id.srlAllNews);
        progressBar = (ProgressBar) root.findViewById(me.rishabhkhanna.peopleword.R.id.all_news_progress);
        if(counter == 0 && fetchFristApi){
            progressBar.setVisibility(View.VISIBLE);
        }
        rvPage = (RecyclerView) root.findViewById(me.rishabhkhanna.peopleword.R.id.rvPageAllNews);
        final AllNewsPageRecyclerAdapter allNewsAdapter = new AllNewsPageRecyclerAdapter(newsArrayList, getContext());

        //get Toi data
        if (counter == 0 && fetchFristApi) {
//            FetchNews.getUrlNews(getContext(), onJsonRecieved, urlFirstPage);
            if (UtilMethods.isNetConnected(getContext())) {
                setupCall(position, counter).enqueue(new Callback<ArrayList<NewsJson>>() {
                    @Override
                    public void onResponse(Call<ArrayList<NewsJson>> call, Response<ArrayList<NewsJson>> response) {
                        if (response.body() != null) {
                            newsArrayList.addAll(response.body());
                        }
                        progressBar.setVisibility(View.GONE);
                        allNewsAdapter.notifyDataSetChanged();
                        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
                        Boolean backup = sharedPreferences.getBoolean("backup",true);
                        Log.d(TAG, "onResponse: backup: " + backup);
                        if(backup){
                            Log.d(TAG, "onResponse: " + call.request());
                            String db_name = position + "_news_backup.realm";

                            RealmConfiguration config = new RealmConfiguration.Builder().name(db_name).build();
                            final Realm realm = Realm.getInstance(config);
                            realm.executeTransactionAsync(new Realm.Transaction() {
                                @Override
                                public void execute(Realm realm) {
                                    RealmList realmList = new RealmList();
                                    realm.deleteAll();
                                    realmList.addAll(newsArrayList);
                                    realm.copyToRealmOrUpdate(realmList);

                                }
                            }, new Realm.Transaction.OnSuccess() {
                                @Override
                                public void onSuccess() {
                                    Log.d(TAG, "onSuccess: News saved to Realm");
                                }
                            });
                        }

                    }

                    @Override
                    public void onFailure(Call<ArrayList<NewsJson>> call, Throwable t) {
                        Log.d(TAG, "onError: " + t.getMessage());
                        Log.d(TAG, "onResponse: " + call.request());
                    }
                });
                fetchFristApi = false;
            } else {
                String db_name = position + "_news_backup.realm";
                RealmConfiguration config = new RealmConfiguration.Builder().name(db_name).build();
                final Realm realm = Realm.getInstance(config);
                newsArrayList.addAll(realm.where(NewsJson.class).findAll());
                allNewsAdapter.notifyDataSetChanged();
                Log.d(TAG, "onCreateView: news from Realm, since not online, size: " + newsArrayList.size());
                progressBar.setVisibility(View.GONE);
                if(newsArrayList.size() == 0) {
                    root = inflater.inflate(me.rishabhkhanna.peopleword.R.layout.fragment_network_not_connected, container, false);
                }
                fetchFristApi = false;
            }
        }
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rvPage.setLayoutManager(linearLayoutManager);
        rvPage.setAdapter(allNewsAdapter);


        scrollListener = new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                counter++;
                Log.d(TAG, "onLoadMore: page: " + page + "Total Items Count " + totalItemsCount + "counter: " + counter);
                setupCall(position, counter).enqueue(new Callback<ArrayList<NewsJson>>() {
                    @Override
                    public void onResponse(Call<ArrayList<NewsJson>> call, Response<ArrayList<NewsJson>> response) {
                        progressBar.setVisibility(View.GONE);
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



        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                counter = 0;
                if (UtilMethods.isNetConnected(getContext())) {
                    setupCall(position, counter).enqueue(new Callback<ArrayList<NewsJson>>() {
                        @Override
                        public void onResponse(Call<ArrayList<NewsJson>> call, Response<ArrayList<NewsJson>> response) {
                            newsArrayList.clear();
                            newsArrayList.addAll(response.body());
                            allNewsAdapter.notifyDataSetChanged();
                            swipeRefreshLayout.setRefreshing(false);
                            Log.d(TAG, "onResponse: " + call.request());
                            Log.d(TAG, "onResponse: " + newsArrayList.size());
                        }

                        @Override
                        public void onFailure(Call<ArrayList<NewsJson>> call, Throwable t) {
                            Log.d(TAG, "onError: " + t.getMessage());
                            Log.d(TAG, "onResponse: " + call.request());
                        }
                    });
                }else{
                    swipeRefreshLayout.setRefreshing(false);
                }

                scrollListener.resetState();

            }
        });

        return root;
    }


    private Call<ArrayList<NewsJson>> setupCall(int position, int counter) {
        Log.d(TAG, "setupCall: " + counter);
        switch (position) {
            case 0:
                call = NewsAPI.getInstance().getNews.getBriefs(String.valueOf(counter),authDetails.getAuthToken(),authDetails.getUserId());
                currentPage = "briefs";
                break;
            case 1:
                call = NewsAPI.getInstance().getNews.getTopNews(String.valueOf(counter),authDetails.getAuthToken(),authDetails.getUserId());
                currentPage = "top_news";
                break;
            case 2:
                call = NewsAPI.getInstance().getNews.getEntertainment(String.valueOf(counter),authDetails.getAuthToken(),authDetails.getUserId());
                currentPage = "entertainment";
                break;
            case 3:
                call = NewsAPI.getInstance().getNews.getIndiaNews(String.valueOf(counter),authDetails.getAuthToken(),authDetails.getUserId());
                currentPage = "india";
                break;
            case 4:
                call = NewsAPI.getInstance().getNews.getWorldNews(String.valueOf(counter),authDetails.getAuthToken(),authDetails.getUserId());
                currentPage = "world";
                break;
            case 5:
                call = NewsAPI.getInstance().getNews.getSports(String.valueOf(counter),authDetails.getAuthToken(),authDetails.getUserId());
                currentPage = "sports";
                break;
            case 6:
                call = NewsAPI.getInstance().getNews.getCricketNews(String.valueOf(counter),authDetails.getAuthToken(),authDetails.getUserId());
                currentPage = "cricket";
                break;
            case 7:
                call = NewsAPI.getInstance().getNews.getBusinessNews(String.valueOf(counter),authDetails.getAuthToken(),authDetails.getUserId());
                currentPage = "business";
                break;
            case 8:
                call = NewsAPI.getInstance().getNews.getEducatoinNews(String.valueOf(counter),authDetails.getAuthToken(),authDetails.getUserId());
                currentPage = "education";
                break;
            case 9:
                call = NewsAPI.getInstance().getNews.getTVNews(String.valueOf(counter),authDetails.getAuthToken(),authDetails.getUserId());
                currentPage = "tv";
                break;
            case 10:
                call = NewsAPI.getInstance().getNews.getAuomotiveNews(String.valueOf(counter),authDetails.getAuthToken(),authDetails.getUserId());
                currentPage = "automotive";
                break;
            case 11:
                call = NewsAPI.getInstance().getNews.getLifestyleNews(String.valueOf(counter),authDetails.getAuthToken(),authDetails.getUserId());
                currentPage = "life_style";
                break;
            case 12:
                call = NewsAPI.getInstance().getNews.getEnvironmentNews(String.valueOf(counter),authDetails.getAuthToken(),authDetails.getUserId());
                currentPage = "environment";
                break;
            case 13:
                call = NewsAPI.getInstance().getNews.getGoodGovNews(String.valueOf(counter),authDetails.getAuthToken(),authDetails.getUserId());
                currentPage = "good_governance";
                break;
            case 14:
                call = NewsAPI.getInstance().getNews.getEvents(String.valueOf(counter),authDetails.getAuthToken(),authDetails.getUserId());
                currentPage = "events";
                break;
        }

        return call;

    }


}
