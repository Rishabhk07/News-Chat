package com.example.rishabhkhanna.peopleword.Adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import com.example.rishabhkhanna.peopleword.R;
import com.example.rishabhkhanna.peopleword.model.Topic;
import com.example.rishabhkhanna.peopleword.utils.Constants;

import java.util.ArrayList;

/**
 * Created by rishabhkhanna on 22/07/17.
 */

public class TopicAdapter extends RecyclerView.Adapter<TopicAdapter.TopicViewHolder>{
    Context context;
    ArrayList<Topic> topicArrayList;
    SharedPreferences sharedPreferences;
    public TopicAdapter(Context context, ArrayList<Topic> topicArrayList) {
        this.context = context;
        this.topicArrayList = topicArrayList;
        sharedPreferences =  context.getSharedPreferences(Constants.TOPIC_NAME,Context.MODE_PRIVATE);
    }

    @Override
    public TopicViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TopicViewHolder(((LayoutInflater)context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                .inflate(R.layout.topic_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(final TopicViewHolder holder, int position) {
            final Topic thisTopic = topicArrayList.get(position);
            holder.toggleButton.setText(thisTopic.getName());
            holder.toggleButton.setTextOn(thisTopic.getName());
            holder.toggleButton.setTextOff(thisTopic.getName());
            if(sharedPreferences.getBoolean(thisTopic.getKey(),false)){
                holder.toggleButton.setChecked(true);
            }else{
                holder.toggleButton.setChecked(false);
            }
            holder.toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked){
                        sharedPreferences.edit().putBoolean(thisTopic.getKey(),true).apply();
                        holder.toggleButton.setChecked(true);
                    }else{
                        sharedPreferences.edit().putBoolean(thisTopic.getKey(),false).apply();
                        holder.toggleButton.setChecked(false);
                    }
                }
            });
    }

    @Override
    public int getItemCount() {
        return topicArrayList.size();
    }

    public class TopicViewHolder extends RecyclerView.ViewHolder{
    ToggleButton toggleButton;
        public TopicViewHolder(View itemView) {
            super(itemView);
            toggleButton = (ToggleButton) itemView.findViewById(R.id.toggleButton);
        }
    }

}
