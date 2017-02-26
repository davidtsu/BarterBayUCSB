package com.barterbayucsb.barterbay;

import java.util.ArrayList;

/**
 * Created by Ming Chen on 2/7/2017.
 */

final class User {
    private String name = "";
    private String id = "";
    private ArrayList<Offer> offers;
    private ArrayList<Review> reviews;
    private String session;

    public User(){
        offers = new ArrayList<Offer>();
        name = "test user";
        id = "test id";
    }

}
