package me.rishabhkhanna.peopleword.Adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import me.rishabhkhanna.peopleword.Interfaces.ItemTouchHelperAdapter;
import me.rishabhkhanna.peopleword.R;
import me.rishabhkhanna.peopleword.model.Topic;
import me.rishabhkhanna.peopleword.utils.Constants;

import java.util.Collections;

import io.realm.Realm;
import io.realm.RealmList;

/**
 * Created by rishabhkhanna on 22/07/17.
 */

public class TopicAdapter extends RecyclerView.Adapter<TopicAdapter.TopicViewHolder> implements ItemTouchHelperAdapter{
    Context context;
    RealmList<Topic> topicArrayList;
    SharedPreferences sharedPreferences;

    public static final String TAG = "TopicAdapter";
    public TopicAdapter(Context context, RealmList<Topic> topicArrayList) {
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

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        Log.d(TAG, "onItemMove: FROM " + fromPosition  + "TO "  + toPosition);

        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        if(fromPosition < toPosition){
            for (int i = fromPosition; i < toPosition ;i++){
                Collections.swap(topicArrayList,i,i+1);
                swapPosition(topicArrayList,i,i+1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(topicArrayList, i, i - 1);
                swapPosition(topicArrayList,i,i-1);
            }
        }
        realm.copyToRealmOrUpdate(topicArrayList);
        realm.commitTransaction();
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.TOPIC_POS_DB,Context.MODE_PRIVATE);
        notifyItemMoved(fromPosition,toPosition);


        return true;

    }

    @Override
    public void onMoved(int fromPos, int toPosition) {
        Log.d(TAG, "onMoved: " + toPosition);


//        realm.commitTransaction();
        Log.d(TAG, "onMoved: " + topicArrayList.get(toPosition));

    }


    public class TopicViewHolder extends RecyclerView.ViewHolder{
    ToggleButton toggleButton;
        public TopicViewHolder(View itemView) {
            super(itemView);
            toggleButton = (ToggleButton) itemView.findViewById(R.id.toggleButton);
        }
    }

    public void swapPosition(RealmList<Topic> arraylist,int i ,int j){
        Log.d(TAG, "swapPosition: shifted from " + i + " "+ j);
        int iPos = arraylist.get(i).getPosition();
        arraylist.get(i).setPosition(arraylist.get(j).getPosition());
        arraylist.get(j).setPosition(iPos);

    }



}
