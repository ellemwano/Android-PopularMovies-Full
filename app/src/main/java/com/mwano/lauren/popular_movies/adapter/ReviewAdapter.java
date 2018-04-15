package com.mwano.lauren.popular_movies.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mwano.lauren.popular_movies.R;
import com.mwano.lauren.popular_movies.model.Review;

import java.util.ArrayList;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder>{

    private Context mContext;
    private ArrayList<Review> mReviews;
    private final ReviewAdapterOnClickHandler mReviewClickHandler;

    /**
     * Create an OnClickHandler interface
     */
    public interface ReviewAdapterOnClickHandler {
        void onClickReview(Review currentReview);
    }

    // ReviewAdapter constructor
    public ReviewAdapter (Context context, ArrayList<Review> reviews,
                          ReviewAdapterOnClickHandler reviewClickHandler) {
        mContext = context;
        mReviews = reviews;
        mReviewClickHandler = reviewClickHandler;
    }

    @Override
    public ReviewAdapter.ReviewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        int layoutIdForReviewItem = R.layout.review_item;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        View mItemView = mInflater.inflate(layoutIdForReviewItem, parent, false);
        return new ReviewAdapter.ReviewViewHolder(mItemView);
    }

    @Override
    public void onBindViewHolder(ReviewAdapter.ReviewViewHolder reviewViewHolder, int position) {
        mContext = reviewViewHolder.mReviewAuthorView.getContext();
        Review mCurrentReview = mReviews.get(position);
        reviewViewHolder.mReviewAuthorView.setText(mCurrentReview.getReviewAuthor());
        reviewViewHolder.mReviewContentView.setText(mCurrentReview.getReviewContent());
    }

    @Override
    public int getItemCount() {
        if (mReviews == null) return 0;
        return mReviews.size();
    }

    public void setReviewData(ArrayList<Review> reviewData) {
        mReviews = reviewData;
        notifyDataSetChanged();
    }

    /**
     * Create a ViewHolder with a click listener
     */
    public class ReviewViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public final TextView mReviewAuthorView;
        public final TextView mReviewContentView;

        // ReviewViewHolder constructor
        public ReviewViewHolder(View view) {
            super(view);
            mReviewAuthorView = (TextView) view.findViewById(R.id.author_name);
            mReviewContentView = (TextView) view.findViewById(R.id.review_content);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            Review currentReview = mReviews.get(adapterPosition);
            mReviewClickHandler.onClickReview(currentReview);
        }
    }
}
