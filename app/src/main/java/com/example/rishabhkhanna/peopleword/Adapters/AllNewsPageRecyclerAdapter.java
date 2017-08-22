package com.example.rishabhkhanna.peopleword.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.BoolRes;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rishabhkhanna.peopleword.Network.API;
import com.example.rishabhkhanna.peopleword.Network.interfaces.rateNews;
import com.example.rishabhkhanna.peopleword.R;
import com.example.rishabhkhanna.peopleword.model.AuthResponse;
import com.example.rishabhkhanna.peopleword.model.NewsJson;
import com.example.rishabhkhanna.peopleword.model.ToiJson;
import com.example.rishabhkhanna.peopleword.utils.Constants;
import com.example.rishabhkhanna.peopleword.utils.UtilMethods;
import com.example.rishabhkhanna.peopleword.views.Activities.DetailNewsActivity;
import com.facebook.AccessToken;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by rishabhkhanna on 19/06/17.
 */

public class AllNewsPageRecyclerAdapter extends RecyclerView.Adapter<AllNewsPageRecyclerAdapter.AllnewsViewholder> {
    private ArrayList<NewsJson> newsArrayList;
    private Context context;
    public static final String TAG = "AllNewsrecyclerAdapter";

    public AllNewsPageRecyclerAdapter(ArrayList<NewsJson> newsArrayList, Context context) {
        this.newsArrayList = newsArrayList;
        this.context = context;
    }

    @Override
    public int getItemViewType(int position) {
        if (newsArrayList.size() > 0) {
            if (newsArrayList.get(position).getmUser() != null && !newsArrayList.get(position).getmUser().isEmpty()) {
                if (newsArrayList.get(position).getmUser().get(0).getmUserTable().getRating() == 1) {
                    return 1;
                } else {
                    return 0;
                }
            }
        }
        return 2;
    }

    @Override
    public AllnewsViewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        int layout = R.layout.news_list_view;
        if (viewType == 1) {
            layout = R.layout.news_list_green;
        } else if (viewType == 0) {
            layout = R.layout.news_list_red;
        }
        View itemView = li.inflate(layout, parent, false);
        return new AllnewsViewholder(itemView);
    }

    @Override
    public void onBindViewHolder(final AllnewsViewholder holder, final int position) {
        final NewsJson thisJsonData = newsArrayList.get(position);
        holder.tvNewsHeading.setText(thisJsonData.getHl());
        holder.tvLikes.setText(String.valueOf(thisJsonData.getLikes()));
        holder.tvDislikes.setText(String.valueOf(thisJsonData.getDislikes()));
        holder.tvChats.setText(String.valueOf(thisJsonData.getChats()));

        if (AccessToken.getCurrentAccessToken() != null) {
            holder.ivLike.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    Log.d(TAG, "onTouch: touch kiya re");
                    like(thisJsonData, position);
                    return true;
                }
            });

            holder.ivDislike.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    dislike(thisJsonData, position);
                    return true;
                }
            });
            holder.tvLikes.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    like(thisJsonData, position);
                    return true;
                }
            });
            holder.tvDislikes.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    dislike(thisJsonData, position);
                    return true;
                }
            });
        }

        Picasso.with(context).load("http://timesofindia.indiatimes.com/thumb.cms?photoid=" +
                thisJsonData.getImageid() + "&width=600&height=500&resizemode=1")
                .noFade()
                .fit()
                .into(holder.ivNewsImage);

        holder.cvNewsList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Gson gson = new Gson();

                Intent i = new Intent(context, DetailNewsActivity.class);
                i.putExtra(Constants.DETAIL_NEWS_KEY, gson.toJson(thisJsonData, NewsJson.class));
                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) context
                        , Pair.create((View) holder.ivNewsImage, "shared"),
                        Pair.create((View) holder.tvNewsHeading, "transHeading"));
                if(UtilMethods.isNetConnected(context)){
                    context.startActivity(i, options.toBundle());
                }else{
                    Toast.makeText(context, "Not Connected to Internet", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return newsArrayList.size();
    }

    static class AllnewsViewholder extends RecyclerView.ViewHolder {
        TextView tvNewsHeading;
        TextView tvLikes;
        TextView tvDislikes;
        TextView tvChats;
        ImageView ivLike;
        ImageView ivDislike;
        ImageView ivNewsImage;
        CardView cvNewsList;

        public AllnewsViewholder(View itemView) {
            super(itemView);
            tvNewsHeading = (TextView) itemView.findViewById(R.id.tvPageNews);
            tvLikes = (TextView) itemView.findViewById(R.id.tvLike);
            tvDislikes = (TextView) itemView.findViewById(R.id.tvDislike);
            ivNewsImage = (ImageView) itemView.findViewById(R.id.ivPageNews);
            tvChats = (TextView) itemView.findViewById(R.id.tvChats);
            cvNewsList = (CardView) itemView.findViewById(R.id.news_list_cv);
            ivLike = (ImageView) itemView.findViewById(R.id.ivLike);
            ivDislike = (ImageView) itemView.findViewById(R.id.ivDislike);

        }
    }

    public void like(NewsJson thisJsonData, final int position) {
        String token = AccessToken.getCurrentAccessToken().getToken();
        String userID = AccessToken.getCurrentAccessToken().getUserId();
        API.getInstance()
                .retrofit
                .create(rateNews.class)
                .likeNews(
                        token,
                        userID,
                        thisJsonData.getMsid(),
                        String.valueOf(thisJsonData.getId()),
                        thisJsonData.getmUser().size() > 0 ? thisJsonData.getmUser().get(0).getmUserTable().getRating() : -1
                ).enqueue(new Callback<NewsJson>() {
            @Override
            public void onResponse(Call<NewsJson> call, Response<NewsJson> response) {
                Log.d(TAG, "onResponse: Cool Your Rating has been recorded");
                Log.d(TAG, "onResponse: " + call.request());
                newsArrayList.set(position, response.body());
                notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<NewsJson> call, Throwable t) {
                Log.d(TAG, "onFailure: " + call.request());
            }
        });

    }

    public void dislike(NewsJson thisJsonData, final int position) {
        String token = AccessToken.getCurrentAccessToken().getToken();
        String userID = AccessToken.getCurrentAccessToken().getUserId();
        API.getInstance()
                .retrofit
                .create(rateNews.class)
                .dislikeNews(
                        token,
                        userID,
                        thisJsonData.getMsid(),
                        String.valueOf(thisJsonData.getId()),
                        thisJsonData.getmUser().size() > 0 ? thisJsonData.getmUser().get(0).getmUserTable().getRating() : -1
                ).enqueue(new Callback<NewsJson>() {
            @Override
            public void onResponse(Call<NewsJson> call, Response<NewsJson> response) {
                Log.d(TAG, "onResponse: Cool Your Rating has been recorded");
                Log.d(TAG, "onResponse: " + call.request());
                Log.d(TAG, "onResponse: " + response);
                newsArrayList.set(position, response.body());
                notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<NewsJson> call, Throwable t) {
                Log.d(TAG, "onFailure: " + call.request());
            }
        });
    }
}
