package me.rishabhkhanna.peopleword.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import me.rishabhkhanna.peopleword.model.NewsJson;
import me.rishabhkhanna.peopleword.utils.Constants;
import me.rishabhkhanna.peopleword.views.Activities.DetailNewsActivity;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


/**
 * Created by rishabhkhanna on 26/12/16.
 */

public class RateNewsAdapter {

    public static final String TAG = "Adapter";

    public static SwipeCardAdapter getSwipeCardAdapter(ArrayList<NewsJson> newsList , Context context){
        SwipeCardAdapter swipeCardAdapter = new SwipeCardAdapter(newsList , context);
        return swipeCardAdapter;
    }



    public static class SwipeCardAdapter extends BaseAdapter{

        private ArrayList<NewsJson> newsList ;
        private Context context;

        public SwipeCardAdapter(ArrayList<NewsJson> newsList , Context context) {
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
        public NewsJson getItem(int position) {
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
                view = inflater.inflate(me.rishabhkhanna.peopleword.R.layout.news_card , parent , false);

            }
            if(!newsList.isEmpty()) {
                final ImageView newsImageView = (ImageView) view.findViewById(me.rishabhkhanna.peopleword.R.id.news_image);
                final TextView newsHeadlineTV =  (TextView) view.findViewById(me.rishabhkhanna.peopleword.R.id.news_headline);
                final TextView newsDetailTV = (TextView) view.findViewById(me.rishabhkhanna.peopleword.R.id.news_full);

                newsHeadlineTV.setText(newsList.get(position).getHl());
                newsDetailTV.setText(newsList.get(position).getSyn());
                String imageUrl = "";
                Log.d(TAG, "getView: data attached");
                Picasso.with(context).load("http://timesofindia.indiatimes.com/thumb.cms?photoid=" +
                        newsList.get(position).getImageid() + "&width=600&height=500&resizemode=1")
                        .noFade()
                        .fit()
                        .into(newsImageView);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    newsImageView.setTransitionName("shared");
                }


            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    deatilNews(newsList.get(position) , newsImageView,newsHeadlineTV,newsDetailTV);
                }
            });


            }

            return view;
        }

        private void deatilNews(NewsJson toiJson , ImageView shared, TextView heading, TextView content) {
            Gson gson = new Gson();
            Intent i = new Intent(context , DetailNewsActivity.class);
            i.putExtra(Constants.DETAIL_NEWS_KEY,gson.toJson(toiJson,NewsJson.class));
            ActivityOptionsCompat options;
            options = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) context,
                    Pair.create((View)shared,"shared"),
                    Pair.create((View)heading,"transHeading"),
                    Pair.create((View)content,"transContent"));
            context.startActivity(i , options.toBundle());

        }


    }

}
