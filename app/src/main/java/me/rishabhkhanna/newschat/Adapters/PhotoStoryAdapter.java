package me.rishabhkhanna.newschat.Adapters;

import android.content.Context;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import me.rishabhkhanna.newschat.R;
import me.rishabhkhanna.newschat.model.PhotoStory;
import me.rishabhkhanna.newschat.utils.UtilMethods;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import at.blogc.android.views.ExpandableTextView;

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
    public void onBindViewHolder(final ViewHolderPhoto holder, int position) {
        PhotoStory thisPhotstory = photoStoryArrayList.get(position);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            holder.tvPhotostory.setText(Html.fromHtml(thisPhotstory.getCap(),Html.FROM_HTML_MODE_COMPACT));
        }else{
            holder.tvPhotostory.setText(Html.fromHtml(thisPhotstory.getCap()));
        }
        Picasso.with(context).
                load(UtilMethods.getImageurl(thisPhotstory.getId(),"1400","960"))
                .placeholder(R.drawable.photo_placeholder)
                .into(holder.ivPhotostory);
        holder.tvPhotostory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        holder.tvPhotostory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.tvPhotostory.isExpanded()){
                    holder.tvPhotostory.collapse();

                }else{
                    holder.tvPhotostory.expand();
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return photoStoryArrayList.size();
    }

    public class ViewHolderPhoto extends RecyclerView.ViewHolder{
        ImageView ivPhotostory;
        ExpandableTextView tvPhotostory;
        public ViewHolderPhoto(View itemView) {
            super(itemView);
            ivPhotostory = (ImageView) itemView.findViewById(R.id.ivPhotostory);
            tvPhotostory = (ExpandableTextView) itemView.findViewById(R.id.tvPhotostory);
        }
    }
}
