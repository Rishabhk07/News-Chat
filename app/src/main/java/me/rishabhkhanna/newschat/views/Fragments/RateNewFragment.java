package me.rishabhkhanna.newschat.views.Fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import com.daprlabs.cardstack.SwipeDeck;
import me.rishabhkhanna.newschat.Adapters.RateNewsAdapter;
import me.rishabhkhanna.newschat.Network.API;
import me.rishabhkhanna.newschat.Network.interfaces.rateNews;
import me.rishabhkhanna.newschat.R;
import me.rishabhkhanna.newschat.model.AuthDetails;
import me.rishabhkhanna.newschat.model.NewsJson;
import me.rishabhkhanna.newschat.utils.Constants;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class RateNewFragment extends Fragment {

    private SwipeDeck swipeDeck;
    public RateNewsAdapter.SwipeCardAdapter swipeCardAdapter;
    Button dislikeBtn, likeBtn;
    ArrayList<NewsJson> newsArrayList = new ArrayList<>();
    CallbackManager callbackManager;
    SharedPreferences sharedPreferences;
    ProgressBar rateProgress;
    int offset = -1;
    public static final String TAG = "RateNewsActivity";

    AuthDetails authDetails;
    public RateNewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        sharedPreferences = getActivity().getSharedPreferences(Constants.AUTH_DETAILS, Context.MODE_PRIVATE);
        if (AccessToken.getCurrentAccessToken() != null) {
            authDetails = new AuthDetails(AccessToken.getCurrentAccessToken().getToken(), AccessToken.getCurrentAccessToken().getUserId());
        }else{
            authDetails = new AuthDetails("null","null");
        }
        if ((sharedPreferences.getString(Constants.LOGIN_TOKEN, "null")).equals("null")) {
            callbackManager = CallbackManager.Factory.create();
        }

        View root = inflater.inflate(R.layout.fragment_rate_new, container, false);
        swipeDeck = (SwipeDeck) root.findViewById(R.id.swipe_deck);
        likeBtn = (Button) root.findViewById(R.id.like_btn);
        dislikeBtn = (Button) root.findViewById(R.id.dislike_btn);
        rateProgress = (ProgressBar) root.findViewById(R.id.progress_bar_rate);


        swipeCardAdapter = RateNewsAdapter.getSwipeCardAdapter(newsArrayList, getContext());
        swipeDeck.setAdapter(swipeCardAdapter);

        //event CallBack
        swipeDeck.setEventCallback(new SwipeDeck.SwipeEventCallback() {
            @Override
            public void cardSwipedLeft(int position) {
//                Log.d(TAG, "cardSwipedLeft: ");
                NewsJson swipedNews = newsArrayList.get(position);
                String token = AccessToken.getCurrentAccessToken().getToken();
                String userID = AccessToken.getCurrentAccessToken().getUserId();
                API.getInstance()
                        .retrofit
                        .create(rateNews.class)
                        .dislikeNews(
                                token,
                                userID,
                                swipedNews.getMsid(),
                                String.valueOf(swipedNews.getId()),
                                -1
                        ).enqueue(new Callback<NewsJson>() {
                    @Override
                    public void onResponse(Call<NewsJson> call, Response<NewsJson> response) {
//                        Log.d(TAG, "onResponse: Cool Your Rating has been recorded");
//                        Log.d(TAG, "onResponse: " + call.request());
                    }

                    @Override
                    public void onFailure(Call<NewsJson> call, Throwable t) {
//                        Log.d(TAG, "onFailure: " + call.request());
                    }
                });
            }

            @Override
            public void cardSwipedRight(int position) {
                NewsJson swipedNews = newsArrayList.get(position);
                String token = AccessToken.getCurrentAccessToken().getToken();
                String userID = AccessToken.getCurrentAccessToken().getUserId();
                API.getInstance()
                        .retrofit
                        .create(rateNews.class)
                        .likeNews(
                                token,
                                userID,
                                swipedNews.getMsid(),
                                String.valueOf(swipedNews.getId()),
                                -1
                        ).enqueue(new Callback<NewsJson>() {
                    @Override
                    public void onResponse(Call<NewsJson> call, Response<NewsJson> response) {
//                        Log.d(TAG, "onResponse: Cool Your Rating has been recorded");
//                        Log.d(TAG, "onResponse: " + call.request());
                    }

                    @Override
                    public void onFailure(Call<NewsJson> call, Throwable t) {
//                        Log.d(TAG, "onFailure: " + call.request());
                    }
                });
            }

            @Override
            public void cardsDepleted() {

//                if all cards are depleted then again call to get further news
                getNewsToBeRated();

            }

            @Override
            public void cardActionDown() {

            }

            @Override
            public void cardActionUp() {

            }
        });
        //get initial news for rating
        getNewsToBeRated();

        //button click listner
        likeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                swipeDeck.swipeTopCardRight(1000);
            }
        });

        dislikeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                swipeDeck.swipeTopCardLeft(1000);
            }
        });


        // set images on of like and dislike on swipe
        swipeDeck.setLeftImage(me.rishabhkhanna.newschat.R.id.nope_card_image);
        swipeDeck.setRightImage(me.rishabhkhanna.newschat.R.id.like_card_image);


        return root;
    }

    public void getNewsToBeRated(){
        offset++;
        final ArrayList<ArrayList<NewsJson>> news = new ArrayList<>();
        rateProgress.setVisibility(View.VISIBLE);
        API.getInstance()
                .retrofit
                .create(rateNews.class)
                .getNews(AccessToken.getCurrentAccessToken().getUserId(),offset)
                .enqueue(new Callback<ArrayList<ArrayList<NewsJson>>>() {
                    @Override
                    public void onResponse(Call<ArrayList<ArrayList<NewsJson>>> call, Response<ArrayList<ArrayList<NewsJson>>> response) {
                        if(response.body().size() == 0){
                            getNewsToBeRated();
                        }else {
                            news.addAll(response.body());
                            for (int i = 0; i < news.size(); i++) {
                                if (!response.body().get(i).isEmpty())
                                    newsArrayList.addAll(news.get(i));
                            }
                            swipeCardAdapter.notifyDataSetChanged();
                            rateProgress.setVisibility(View.GONE);
                        }
                    }
                    @Override
                    public void onFailure(Call<ArrayList<ArrayList<NewsJson>>> call, Throwable t) {

                    }
                });
    }

}
