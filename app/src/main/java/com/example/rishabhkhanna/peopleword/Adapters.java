package com.example.rishabhkhanna.peopleword;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.rishabhkhanna.peopleword.model.ToiJson;

import java.util.ArrayList;

/**
 * Created by rishabhkhanna on 26/12/16.
 */

public class Adapters {

    static SwipeCardAdapter getSwipeCardAdapter(){
        SwipeCardAdapter swipeCardAdapter = new SwipeCardAdapter();


        return swipeCardAdapter;
    }

    public static class SwipeCardAdapter extends BaseAdapter{

        private ArrayList<ToiJson> newsList ;
        private Context context;

        public SwipeCardAdapter() {
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
        public View getView(int position, View view, ViewGroup parent) {
            return null;
        }
    }

}
