package com.barterbayucsb.barterbay;


/**
 * Created by Daniel Ben-Naim on 2/23/2017.
 */


final class Review {

    private String text = "";
    private String id = "";
    private int rating;
    public Review() {

        text = "test review";
        id = "test id";
        rating = 1;

    }
    public Review(String t, String i, int r) {

        text = t;
        id = i;
        rating = r;

    }


    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

}
