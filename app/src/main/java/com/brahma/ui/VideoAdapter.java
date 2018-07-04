package com.brahma.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.brahma.R;
import com.brahma.Room.VideoEntity;
import com.bumptech.glide.Glide;

import java.util.List;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoAdapterViewHolder>{
    private final Context mContext;
    private int rowLayout;
    private List<VideoEntity> mVideos;

    public VideoAdapter( int rowLayout, Context context) {
        this.rowLayout = rowLayout;
        this.mContext = context;
    }
    @NonNull
    @Override
    public VideoAdapter.VideoAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new VideoAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoAdapter.VideoAdapterViewHolder holder, int position) {
        holder.description.setText(mVideos.get(position).getDescription());
        holder.title.setText(mVideos.get(position).getTitle());

//        holder.url.setText(mVideos.get(position).getVideoUrl().get(position));


        // loading album cover using Glide library
        Glide.with(mContext).load(mVideos.get(position).getImage_url()).into(holder.videoImage);

    }

    @Override
    public int getItemCount() {
        if (null == mVideos) return 0;
        return mVideos.size();
    }

    class VideoAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        LinearLayout newsLayout;
        TextView state, title, description, url,city,category;
        ImageView videoImage, news_thumbnail;
        private WebView mWebview;
        public TextView buttonViewOption;

        public VideoAdapterViewHolder(View itemView) {
            super(itemView);
            newsLayout = (LinearLayout) itemView.findViewById(R.id.news_layout);
            url = (TextView) itemView.findViewById(R.id.url);
            title = (TextView) itemView.findViewById(R.id.title);
            description = (TextView) itemView.findViewById(R.id.description);
            videoImage = (ImageView) itemView.findViewById(R.id.news_image);
            buttonViewOption = (TextView) itemView.findViewById(R.id.textViewOptions);
            itemView.setOnClickListener(this);

        }


        @Override
        public void onClick(View view) {

        }
    }

    void swapVideos(final List<VideoEntity> videos) {
        // If there was no forecast data, then recreate all of the list

            mVideos = videos;



        notifyDataSetChanged();
    }
    }
