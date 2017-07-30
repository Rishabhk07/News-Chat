package com.example.rishabhkhanna.peopleword.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rishabhkhanna.peopleword.R;
import com.example.rishabhkhanna.peopleword.model.PhotoStory;
import com.example.rishabhkhanna.peopleword.utils.UtilMethods;
import com.example.rishabhkhanna.peopleword.views.Activities.PhotoStoryActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by rishabhkhanna on 15/07/17.
 */

public class PhotoStoryAdapter extends RecyclerView.Adapter<PhotoStoryAdapter.ViewHolderPhoto> {

    Context context;
    ArrayList<PhotoStory> photoStoryArrayList;

    public PhotoStoryAdapter(Context context, ArrayList<PhotoStory> photoStoryArrayList) {
        this.context = context;
        this.photoStoryArrayList = photoStoryArrayList;
    }

    @Override
    public ViewHolderPhoto onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View root = li.inflate(R.layout.photo_story_layout,parent,false);

        return new ViewHolderPhoto(root);
    }

    @Override
    public void onBindViewHolder(ViewHolderPhoto holder, int position) {
        PhotoStory thisPhotstory = photoStoryArrayList.get(position);
        holder.tvPhotostory.setText(thisPhotstory.getCap());
        Picasso.with(context).
                load(UtilMethods.getImageurl(thisPhotstory.getId()))
                .into(holder.ivPhotostory);
        holder.tvPhotostory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return photoStoryArrayList.size();
    }

    public class ViewHolderPhoto extends RecyclerView.ViewHolder{
        ImageView ivPhotostory;
        TextView tvPhotostory;
        public ViewHolderPhoto(View itemView) {
            super(itemView);
            ivPhotostory = (ImageView) itemView.findViewById(R.id.ivPhotostory);
            tvPhotostory = (TextView) itemView.findViewById(R.id.tvPhotoStory);
        }
    }
}
