package me.rishabhkhanna.peopleword.Adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import me.rishabhkhanna.peopleword.model.NewsJson;

import java.util.ArrayList;

/**
 * Created by rishabhkhanna on 14/08/17.
 */

public class ProfilePageAdapter extends RecyclerView.Adapter<ProfilePageAdapter.ProfileViewHolder> {

    ArrayList<NewsJson> profileNews;
    Context context;
    int pageNumber = 0;

    public ProfilePageAdapter(ArrayList<NewsJson> profileNews, Context context, int userNewsinfo) {
        this.profileNews = profileNews;
        this.context = context;
        this.pageNumber = userNewsinfo;
    }

    @Override
    public ProfileViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        int layout = me.rishabhkhanna.peopleword.R.layout.news_list_view;
        return new ProfileViewHolder(li.inflate(layout, parent, false));
    }

    @Override
    public void onBindViewHolder(ProfileViewHolder holder, int position) {
        NewsJson thisJsonData = profileNews.get(position);

    }

    @Override
    public int getItemCount() {
        return profileNews.size();
    }

    public class ProfileViewHolder extends RecyclerView.ViewHolder {
        TextView tvNewsHeading;
        TextView tvLikes;
        TextView tvDislikes;
        TextView tvChats;
        ImageView ivLike;
        ImageView ivDislike;
        ImageView ivNewsImage;
        CardView cvNewsList;

        public ProfileViewHolder(View itemView) {
            super(itemView);
            tvNewsHeading = (TextView) itemView.findViewById(me.rishabhkhanna.peopleword.R.id.tvPageNews);
            tvLikes = (TextView) itemView.findViewById(me.rishabhkhanna.peopleword.R.id.tvLike);
            tvDislikes = (TextView) itemView.findViewById(me.rishabhkhanna.peopleword.R.id.tvDislike);
            ivNewsImage = (ImageView) itemView.findViewById(me.rishabhkhanna.peopleword.R.id.ivPageNews);
            tvChats = (TextView) itemView.findViewById(me.rishabhkhanna.peopleword.R.id.tvChats);
            cvNewsList = (CardView) itemView.findViewById(me.rishabhkhanna.peopleword.R.id.news_list_cv);
            ivLike = (ImageView) itemView.findViewById(me.rishabhkhanna.peopleword.R.id.ivLike);
            ivDislike = (ImageView) itemView.findViewById(me.rishabhkhanna.peopleword.R.id.ivDislike);
        }
    }

}
