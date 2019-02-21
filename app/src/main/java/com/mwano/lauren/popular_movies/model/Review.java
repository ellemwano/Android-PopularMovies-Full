package com.mwano.lauren.popular_movies.model;

public class Review {

    private String mReviewId;
    private String mReviewAuthor;
    private String mReviewContent;
    private String mReviewUrl;

    public Review (String reviewId,
                   String reviewAuthor,
                   String reviewContent,
                   String reviewUrl) {
        mReviewId = reviewId;
        mReviewAuthor = reviewAuthor;
        mReviewContent = reviewContent;
        mReviewUrl = reviewUrl;
    }

    public String getReviewId() {
        return mReviewId;
    }

    public void setReviewId (String reviewId) {
        mReviewId = reviewId;
    }

    public String getReviewAuthor() {
        return mReviewAuthor;
    }

    public void setReviewAuthor (String reviewAuthor) {
        mReviewAuthor = reviewAuthor;
    }

    public String getReviewContent() {
        return mReviewContent;
    }

    public void setReviewContent(String reviewContent) {
        mReviewContent = reviewContent;
    }

    public String getReviewUrl() {
        return mReviewUrl;
    }

    public void setReviewUrl(String reviewUrl) {
        mReviewUrl = reviewUrl;
    }

    @Override
    public String toString() {
        return mReviewId + "--"
                + mReviewAuthor + "--"
                + mReviewContent + "--"
                + mReviewUrl + "--";
    }
}
