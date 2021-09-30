package com.example.wikings.Shehani.Model;

public class Rating {
    private float startCount;
    private String review;


    public Rating() {
    }

    public Rating(float startCount, String review) {
        this.startCount = startCount;
        this.review = review;
    }

    public float getStartCount() {
        return startCount;
    }

    public void setStartCount(float startCount) {
        this.startCount = startCount;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }
}

