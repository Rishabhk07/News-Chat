package com.example.rishabhkhanna.peopleword.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.ActivityOptionsCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rishabhkhanna.peopleword.R;
import com.example.rishabhkhanna.peopleword.model.ToiJson;
import com.example.rishabhkhanna.peopleword.views.Activities.DetailNewsActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


/**
 * Created by rishabhkhanna on 26/12/16.
 */

public class RateNewsAdapter {

    public static final String TAG = "Adapter";

    public static SwipeCardAdapter getSwipeCardAdapter(ArrayList<ToiJson> newsList , Context context){
        SwipeCardAdapter swipeCardAdapter = new SwipeCardAdapter(newsList , context);
        return swipeCardAdapter;
    }



    public static class SwipeCardAdapter extends BaseAdapter{

        private ArrayList<ToiJson> newsList ;
        private Context context;

        public SwipeCardAdapter(ArrayList<ToiJson> newsList , Context context) {
            this.newsList = newsList;
            this.context = context;
//            Log.d(TAG, this.newsList.get(0).getHl().toString());
//            Log.d(TAG, this.context.toString());
        }

        @Override
        public int getCount() {
            return newsList.size();
        }

        @Override
        public ToiJson getItem(int position) {
            return newsList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            View view = convertView;

            if(view == null){
                Log.d(TAG, "getView: ");
                LayoutInflater inflater = LayoutInflater.from(context);
                Log.d(TAG, "getView: 2222");
                view = inflater.inflate(R.layout.news_card , parent , false);

            }
            if(!newsList.isEmpty()) {
                final ImageView newsImageView = (ImageView) view.findViewById(R.id.news_image);
                TextView newsHeadlineTV =  (TextView) view.findViewById(R.id.news_headline);
                TextView newsDetailTV = (TextView) view.findViewById(R.id.news_full);

                newsHeadlineTV.setText(newsList.get(position).getHl());
                newsDetailTV.setText(newsList.get(position).getSyn());
                String imageUrl = "";
                Log.d(TAG, "getView: data attached");
                Picasso.with(context).load("http://timesofindia.indiatimes.com/thumb.cms?photoid=" +
                        newsList.get(position).getImageid() + "&width=1500&height=1440&resizemode=1")
                        .fit()
                        .into(newsImageView);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    newsImageView.setTransitionName("shared");
                }


            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    deatilNews(newsList.get(position) , newsImageView);
                }
            });


            }

            return view;
        }

        private void deatilNews(ToiJson toiJson , ImageView shared) {
            Intent i = new Intent(context , DetailNewsActivity.class);
            i.putExtra("head" , toiJson.getHl());
            i.putExtra("detail" , toiJson.getSyn());
            i.putExtra("img" , toiJson.getImageid());
            ActivityOptionsCompat options;
            options = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) context, shared , "shared");
            context.startActivity(i , options.toBundle());

        }


    }

}
