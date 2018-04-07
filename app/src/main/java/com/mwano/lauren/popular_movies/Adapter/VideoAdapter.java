package com.mwano.lauren.popular_movies.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mwano.lauren.popular_movies.R;
import com.mwano.lauren.popular_movies.model.Video;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoViewHolder>{

    private Context mContext;
    private ArrayList<Video> mVideos;
    private final VideoAdapterOnClickHandler mVideoClickHandler;

    /**
     * Create an OnClickHandler interface
     */
    public interface VideoAdapterOnClickHandler {
        void onClickVideo(Video currentVideo);
    }

    // VideoAdapter constructor
    public VideoAdapter (Context context, ArrayList<Video> videos, VideoAdapterOnClickHandler videoClickHandler){
        mContext = context;
        mVideos = videos;
        mVideoClickHandler = videoClickHandler;
    }

    @Override
    public VideoAdapter.VideoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        int layoutIdForVideoItem = R.layout.video_item;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        View mItemView = mInflater.inflate(layoutIdForVideoItem, parent, false);
        return new VideoAdapter.VideoViewHolder(mItemView);
    }

    @Override
    public void onBindViewHolder(VideoAdapter.VideoViewHolder videoViewHolder, int position) {
        mContext = videoViewHolder.mVideoImageView.getContext();
        Video mCurrentVideo = mVideos.get(position);
        //TODO add placeholder
        Picasso.with(mContext).load(Video.buildVideoThumbnailPath(mCurrentVideo))
                .into(videoViewHolder.mVideoImageView);
        videoViewHolder.mVideoTitleView.setText(mCurrentVideo.getVideoName());
    }

    @Override
    public int getItemCount() {
        if (mVideos == null) return 0;
        return mVideos.size();
    }

    public void setVideoData(ArrayList<Video> videoData) {
        mVideos = videoData;
        notifyDataSetChanged();
    }

    /**
     * Create a ViewHolder with a click listener
     */
    public class VideoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public final ImageView mVideoImageView;
        public final TextView mVideoTitleView;

        // VideoViewHolder constructor
        public VideoViewHolder(View view) {
            super(view);
            mVideoImageView = (ImageView)view.findViewById(R.id.video_thumbnail);
            mVideoTitleView = (TextView)view.findViewById(R.id.video_title);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            Video currentVideo = mVideos.get(adapterPosition);
            mVideoClickHandler.onClickVideo(currentVideo);
        }
    }
}
