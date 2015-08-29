package com.beekay.ouceplacements;


import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by Krishna on 8/6/2015.
 */
public class ContentsAdapter extends RecyclerView.Adapter<ContentsAdapter.ContentViewHolder>{

        private List<Contents> contentsList;
    int lastPostition=-1;

    public void setContext(Context context) {
        this.context = context;
    }

    Context context;

    ContentsAdapter(List<Contents> contentsList){
        this.contentsList=contentsList;
    }

    @Override
    public ContentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        setContext(parent.getContext());
        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.card,parent,false);

        return new ContentViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ContentViewHolder holder, int position) {
            Contents contents=contentsList.get(position);
        holder.notificationView.setText(contents.notificationContent);

        holder.attachmentView.setText(contents.attachments);
        Pattern wordMatch=Pattern.compile("^upload/[-a-zA-Z0-9+&@#/%?=~_|!:,.; ]*[-a-zA-Z0-9+&@#/%=~_| ]");
        String url="http://oucecareers.org/";
        Linkify.addLinks(holder.attachmentView,wordMatch,url);
        holder.attachmentView.setLinkTextColor(Color.RED);
        holder.dateView.setText(contents.datePosted);
        setAnimation(holder.card,position);
    }

    @Override
    public int getItemCount() {
        return contentsList.size();
    }

    void setAnimation(View toAnimate,int position){
        if(position>lastPostition){
            Animation animation= AnimationUtils.loadAnimation(context,android.R.anim.fade_in);
            toAnimate.setAnimation(animation);
            lastPostition=position;
        }
    }

    public static class ContentViewHolder extends RecyclerView.ViewHolder{

        protected TextView notificationView;
        protected TextView attachmentView;
        protected TextView dateView;
        protected CardView card;

        public ContentViewHolder(View itemView) {
            super(itemView);
            card=(CardView)itemView.findViewById(R.id.card);
            notificationView=(TextView)itemView.findViewById(R.id.notLink);
            attachmentView=(TextView)itemView.findViewById(R.id.attachment);
            dateView=(TextView)itemView.findViewById(R.id.published);
        }
    }
}
