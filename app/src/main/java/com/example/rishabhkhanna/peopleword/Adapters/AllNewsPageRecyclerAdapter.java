package com.example.rishabhkhanna.peopleword.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rishabhkhanna.peopleword.R;
import com.example.rishabhkhanna.peopleword.model.ToiJson;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by rishabhkhanna on 19/06/17.
 */

public class AllNewsPageRecyclerAdapter extends RecyclerView.Adapter<AllNewsPageRecyclerAdapter.AllnewsViewholder> {
    private ArrayList<ToiJson> newsArrayList;
    private Context context;
    public static final String TAG = "AllNewsrecyclerAdapter";

    public AllNewsPageRecyclerAdapter(ArrayList<ToiJson> newsArrayList, Context context) {
        this.newsArrayList = newsArrayList;
        this.context = context;
    }

    @Override
    public AllnewsViewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = li.inflate(R.layout.news_list_view,parent,false);
        return new AllnewsViewholder(itemView);
    }

    @Override
    public void onBindViewHolder(AllnewsViewholder holder, int position) {
        ToiJson thisJsonData = newsArrayList.get(position);
        holder.tvNewsHeading.setText(thisJsonData.getHl());
        holder.tvLikes.setText(String.valueOf(new Random().nextInt(1000)));
        holder.tvDislikes.setText(String.valueOf(new Random().nextInt(1000)));
        Picasso.with(context).load("http://timesofindia.indiatimes.com/thumb.cms?photoid=" +
                thisJsonData.getImageid() + "&width=600&height=500&resizemode=1")
                .fit()
                .into(holder.ivNewsImage);
        Log.d(TAG, "onBindViewHolder: " + newsArrayList.get(position).getImageid());
    }

    @Override
    public int getItemCount() {
        return newsArrayList.size();
    }

    static class AllnewsViewholder extends RecyclerView.ViewHolder{
        TextView tvNewsHeading;
        TextView tvLikes;
        TextView tvDislikes;
        TextView tvChats;
        ImageView ivNewsImage;

        public AllnewsViewholder(View itemView) {
            super(itemView);
            tvNewsHeading = (TextView) itemView.findViewById(R.id.tvPageNews);
            tvLikes = (TextView) itemView.findViewById(R.id.tvLike);
            tvDislikes = (TextView) itemView.findViewById(R.id.tvDislike);
            ivNewsImage = (ImageView) itemView.findViewById(R.id.ivPageNews);
            tvChats = (TextView) itemView.findViewById(R.id.tvChats);
        }
    }
}
