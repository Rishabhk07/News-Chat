package com.example.rishabhkhanna.peopleword.Adapters;

import android.content.Context;
import android.support.v4.view.LayoutInflaterCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rishabhkhanna.peopleword.R;
import com.example.rishabhkhanna.peopleword.model.ToiJson;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by rishabhkhanna on 26/12/16.
 */

public class Adapters {

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
            Log.d(TAG, this.newsList.get(0).getHl().toString());
            Log.d(TAG, this.context.toString());
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
        public View getView(int position, View convertView, ViewGroup parent) {

            View view = convertView;

            if(view == null){
                Log.d(TAG, "getView: ");
                LayoutInflater inflater = LayoutInflater.from(context);
                Log.d(TAG, "getView: 2222");
                view = inflater.inflate(R.layout.news_card , parent , false);
            }

            TextView newsHeadlineTV= (TextView) view.findViewById(R.id.news_headline);
            ImageView newImageIV = (ImageView) view.findViewById(R.id.news_image);

            newsHeadlineTV.setText(newsList.get(position).getHl());
            Picasso.with(context).load("http://timesofindia.indiatimes.com/thumb.cms?photoid="+ newsList.get(position).getImageid() +"&width=1440&height=810&resizemode=1").into(newImageIV);

            return view;
        }
    }

}
