package me.rishabhkhanna.peopleword.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import me.rishabhkhanna.peopleword.Network.API;
import me.rishabhkhanna.peopleword.Network.interfaces.rateNews;
import me.rishabhkhanna.peopleword.R;
import me.rishabhkhanna.peopleword.model.NewsJson;
import me.rishabhkhanna.peopleword.utils.Constants;
import me.rishabhkhanna.peopleword.utils.UtilMethods;
import me.rishabhkhanna.peopleword.views.Activities.DetailNewsActivity;

import com.facebook.AccessToken;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

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
    Boolean ratingCallRunning = false;

    public AllNewsPageRecyclerAdapter(ArrayList<NewsJson> newsArrayList, Context context) {
        this.newsArrayList = newsArrayList;
        this.context = context;
    }

    @Override
    public int getItemViewType(int position) {
        if (newsArrayList.get(position).getChats() == -100) {
            Log.d(TAG, "getItemViewType: " + position);
            return 3;
        } else if (newsArrayList.size() > 0) {
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
        } else if (viewType == 3) {
            layout = R.layout.layout_recyclerview_progress;
        }
        View itemView = li.inflate(layout, parent, false);
        return new AllnewsViewholder(itemView);
    }

    @Override
    public void onBindViewHolder(final AllnewsViewholder holder, final int position) {
        final NewsJson thisJsonData = newsArrayList.get(position);
        if (getItemViewType(position) != 3) {
            holder.tvNewsHeading.setText(thisJsonData.getHl());
            holder.tvLikes.setText(String.valueOf(thisJsonData.getLikes()));
            holder.tvDislikes.setText(String.valueOf(thisJsonData.getDislikes()));
            holder.tvChats.setText(String.valueOf(thisJsonData.getChats()));
            if (AccessToken.getCurrentAccessToken() != null) {
                holder.ivLike.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        Log.d(TAG, "onTouch: " + ratingCallRunning);
                        if (!ratingCallRunning) {
                            like(thisJsonData, position);
                            ratingCallRunning = true;
                        }
                        return true;
                    }
                });

                holder.ivDislike.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        Log.d(TAG, "onTouch: " + ratingCallRunning);
                        if (!ratingCallRunning) {
                            dislike(thisJsonData, position);
                            ratingCallRunning = true;
                        }

                        return true;
                    }
                });
                holder.tvLikes.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        Log.d(TAG, "onTouch: " + ratingCallRunning);
                        if (!ratingCallRunning) {
                            like(thisJsonData, position);
                            ratingCallRunning = true;
                        }
                        return true;
                    }
                });
                holder.tvDislikes.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        Log.d(TAG, "onTouch: " + ratingCallRunning);
                        if (!ratingCallRunning) {
                            dislike(thisJsonData, position);
                            ratingCallRunning = true;
                        }
                        return true;
                    }
                });
            }

            Picasso.with(context).load(UtilMethods.getImageurl(thisJsonData.getImageid(), "600", "500"))
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
                    if (UtilMethods.isNetConnected(context)) {
                        context.startActivity(i, options.toBundle());
                    } else {
                        Toast.makeText(context, "Not Connected to Internet", Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }
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
        Log.d(TAG, "like: ");
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
                Log.d(TAG, "onResponse: " + response.body());
                newsArrayList.set(position, response.body());
                notifyDataSetChanged();
                ratingCallRunning = false;
            }

            @Override
            public void onFailure(Call<NewsJson> call, Throwable t) {
                Log.d(TAG, "onFailure: " + call.request());
                ratingCallRunning = false;
            }
        });

    }

    public void dislike(NewsJson thisJsonData, final int position) {
        String token = AccessToken.getCurrentAccessToken().getToken();
        String userID = AccessToken.getCurrentAccessToken().getUserId();
        Log.d(TAG, "dislike: ");
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
                ratingCallRunning = false;
            }

            @Override
            public void onFailure(Call<NewsJson> call, Throwable t) {
                Log.d(TAG, "onFailure: " + call.request());
                ratingCallRunning = false;
            }
        });
    }
}
