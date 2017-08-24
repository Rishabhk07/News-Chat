package me.rishabhkhanna.peopleword.views.Fragments;

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

import me.rishabhkhanna.peopleword.Adapters.AllNewsPageRecyclerAdapter;
import me.rishabhkhanna.peopleword.Network.NewsAPI;
import me.rishabhkhanna.peopleword.model.AuthDetails;
import me.rishabhkhanna.peopleword.model.NewsJson;
import me.rishabhkhanna.peopleword.model.Topic;
import me.rishabhkhanna.peopleword.utils.Constants;
import me.rishabhkhanna.peopleword.utils.EndlessRecyclerViewScrollListener;
import com.facebook.AccessToken;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class YourNewsPageFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match

    static ArrayList<Topic> topics = null;
    int position;
    int counter;
    Call<ArrayList<NewsJson>> call;
    boolean fetchedFirstApi = true;
    EndlessRecyclerViewScrollListener scrollListener;
    public static final String TAG = "YourNewsPageFragment";
    SwipeRefreshLayout swipeRefreshLayout;
    ArrayList<NewsJson> newsJsonArrayList =
            new ArrayList<>();
    RecyclerView recyclerView;
    AuthDetails authDetails;
    public YourNewsPageFragment() {
        // Required empty public constructor
    }


    public static YourNewsPageFragment newInstance(ArrayList<Topic> userTopics, int position) {
        YourNewsPageFragment fragment = new YourNewsPageFragment();
        Log.d(TAG, "newInstance: YourNewsPageFragment");
        Bundle args = new Bundle();
//        args.putString(Constants.USER_TOPICS_LIST,new Gson().toJson(userTopics,Topic.class));
        topics = userTopics;
        args.putInt(Constants.USER_TOPIC_POS,position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: Before Arguments");
        if (getArguments() != null) {
//            this.userTopics = new Gson().fromJson(getArguments().getString(Constants.USER_TOPICS_LIST),Topic[].class);
            position = getArguments().getInt(Constants.USER_TOPIC_POS);
            Log.d(TAG, "onCreate: after arguments");
        }
        if(AccessToken.getCurrentAccessToken() != null){
            authDetails = new AuthDetails(AccessToken.getCurrentAccessToken().getToken(),AccessToken.getCurrentAccessToken().getUserId());
        }else{
            Log.d(TAG, "onCreate: in null Auth Details");
            authDetails = new AuthDetails("null","null");
        }
        Log.d(TAG, "onCreate: after null Auth Details");
        counter = 0;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        getActivity().setTitle("Your News");
        View root = inflater.inflate(me.rishabhkhanna.peopleword.R.layout.fragment_page_all_news, container, false);
        swipeRefreshLayout = (SwipeRefreshLayout) root.findViewById(me.rishabhkhanna.peopleword.R.id.srlAllNews);
        Log.d(TAG, "onCreateView: In OnCreate");
        recyclerView = (RecyclerView) root.findViewById(me.rishabhkhanna.peopleword.R.id.rvPageAllNews);
        final AllNewsPageRecyclerAdapter pageRecyclerAdapter = new AllNewsPageRecyclerAdapter(newsJsonArrayList,getContext());
        if(counter == 0 && fetchedFirstApi){
            setupCall(position,counter).enqueue(new Callback<ArrayList<NewsJson>>() {
                @Override
                public void onResponse(Call<ArrayList<NewsJson>> call, Response<ArrayList<NewsJson>> response) {
                    newsJsonArrayList.clear();
                    newsJsonArrayList.addAll(response.body());
                    pageRecyclerAdapter.notifyDataSetChanged();
                    Log.d(TAG, "onResponse: " + call.request());
                }

                @Override
                public void onFailure(Call<ArrayList<NewsJson>> call, Throwable t) {
                    Log.d(TAG, "onFailure: " + call.request());

                }
            });
            fetchedFirstApi = false;
        }

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(pageRecyclerAdapter);

        return root;
    }


    private Call<ArrayList<NewsJson>> setupCall(int position, int counter) {
        String topicName = topics.get(position).getKey();
        switch(topicName){
            case "briefs":
                call = NewsAPI.getInstance().getNews.getBriefs(String.valueOf(counter),authDetails.getAuthToken(),authDetails.getUserId());
                break;
            case "top_news":
                call = NewsAPI.getInstance().getNews.getTopNews(String.valueOf(counter),authDetails.getAuthToken(),authDetails.getUserId());
                break;
            case "entertainment":
                call = NewsAPI.getInstance().getNews.getEntertainment(String.valueOf(counter),authDetails.getAuthToken(),authDetails.getUserId());
                break;
            case "india":
                call = NewsAPI.getInstance().getNews.getIndiaNews(String.valueOf(counter),authDetails.getAuthToken(),authDetails.getUserId());
                break;
            case "good_governance":
                call = NewsAPI.getInstance().getNews.getGoodGovNews(String.valueOf(counter),authDetails.getAuthToken(),authDetails.getUserId());
                break;
            case "tv":
                call = NewsAPI.getInstance().getNews.getTVNews(String.valueOf(counter),authDetails.getAuthToken(),authDetails.getUserId());
                break;
            case "world":
                call = NewsAPI.getInstance().getNews.getWorldNews(String.valueOf(counter),authDetails.getAuthToken(),authDetails.getUserId());
                break;
            case "sports":
                call = NewsAPI.getInstance().getNews.getSports(String.valueOf(counter),authDetails.getAuthToken(),authDetails.getUserId());
                break;
            case "cricket":
                call = NewsAPI.getInstance().getNews.getCricketNews(String.valueOf(counter),authDetails.getAuthToken(),authDetails.getUserId());
                break;
            case "business":
                call = NewsAPI.getInstance().getNews.getBusinessNews(String.valueOf(counter),authDetails.getAuthToken(),authDetails.getUserId());
                break;
            case "education":
                call = NewsAPI.getInstance().getNews.getEducatoinNews(String.valueOf(counter),authDetails.getAuthToken(),authDetails.getUserId());
                break;
            case "life_style":
                call = NewsAPI.getInstance().getNews.getLifestyleNews(String.valueOf(counter),authDetails.getAuthToken(),authDetails.getUserId());
                break;
            case "automotive":
                call = NewsAPI.getInstance().getNews.getAuomotiveNews(String.valueOf(counter),authDetails.getAuthToken(),authDetails.getUserId());
                break;
            case "environment":
                call = NewsAPI.getInstance().getNews.getEnvironmentNews(String.valueOf(counter),authDetails.getAuthToken(),authDetails.getUserId());
                break;

        }


        return call;

    }


}
