package com.barterbayucsb.barterbay;

import java.util.ArrayList;

/**
 * Created by Ming Chen on 2/7/2017.
 */

final class User {
    private String name = "";
    private String id = "";
    private PhoneNumber phone = new PhoneNumber();
    private ArrayList<Offer> offers;
    private ArrayList<Review> reviews;
    private String session;

    public User() {
        offers = new ArrayList<Offer>();
        name = "test user";
        id = "test id";
    }

    public void addToOffers(Offer offer) {
        offers.add(offer);
    }

    public void addToReviews(Review review) {
        reviews.add(review);
    }
}