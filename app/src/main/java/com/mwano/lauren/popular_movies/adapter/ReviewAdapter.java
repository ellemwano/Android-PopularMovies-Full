package com.mwano.lauren.popular_movies.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
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
    private static final int EXPANDED_POSITION_VALUE = -1;
    private int expandedItemPosition = EXPANDED_POSITION_VALUE;
    private int previousExpandedItemPosition = EXPANDED_POSITION_VALUE;

    public ReviewAdapter (Context context, ArrayList<Review> reviews) {
        mContext = context;
        mReviews = reviews;
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
    public void onBindViewHolder(@NonNull ReviewAdapter.ReviewViewHolder reviewViewHolder, int position) {
        mContext = reviewViewHolder.mReviewAuthorView.getContext();
        Review mCurrentReview = mReviews.get(position);
        reviewViewHolder.bind(mCurrentReview);

        /*
        Set expandable/collapsable TextView. 2 review textviews = reduced review and full review.
        When reduced review is clicked, visibility set to gone and full review set to visible.
        Text automatically collapses when other review clicked or when clicked again.
        Need mReviewRecyclerView.setHasFixedSize() set to false in DetailActivity.
        Source: https://stackoverflow.com/a/48092441/8691157
         */
        final boolean isExpanded = position == expandedItemPosition;
        reviewViewHolder.mReviewContentView.setVisibility(isExpanded ? View.GONE : View.VISIBLE);
        reviewViewHolder.mReviewContentViewFull.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
        reviewViewHolder.itemView.setActivated(isExpanded); //Set to true (if to be activated) or false

        if (isExpanded) {
            previousExpandedItemPosition = position;
        }

        reviewViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expandedItemPosition = isExpanded ? EXPANDED_POSITION_VALUE : position;
                notifyItemChanged(previousExpandedItemPosition);
                notifyItemChanged(position);
            }
        });
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
    public class ReviewViewHolder extends RecyclerView.ViewHolder {

        public final TextView mReviewAuthorView;
        public final TextView mReviewContentView;
        public final TextView mReviewContentViewFull;

        // ReviewViewHolder constructor
        public ReviewViewHolder(View view) {
            super(view);
            mReviewAuthorView = (TextView) view.findViewById(R.id.author_name);
            mReviewContentView = (TextView) view.findViewById(R.id.review_content);
            mReviewContentViewFull = (TextView) view.findViewById(R.id.review_content_full);
        }

        private void bind (Review review) {
            mReviewAuthorView.setText(review.getReviewAuthor());
            mReviewContentView.setText(review.getReviewContent());
            mReviewContentViewFull.setText(review.getReviewContent());
        }
    }
}
