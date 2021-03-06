package com.barterbayucsb.barterbay;

import java.util.ArrayList;

/**
 * Created by Ming Chen on 2/7/2017.
 */

final class User {
    private String name = "";
    private String id = "";
    private String email = "";
    //private PhoneNumber phone = new PhoneNumber();
    private ArrayList<Offer> offers;
    private ArrayList<Review> reviews;
    private String session;
    private String cookie_key;
    static User CURRENT_USER = null;

    public String get_name(){
        return name;
    }
    public String getEmail() { return email; }
    public String getId() {return id; };
    public User() {
        reviews = new ArrayList<>();
        offers = new ArrayList<Offer>();
        name = "test user";
        id = "test id";
    }
    public User(String id, String name, String email, String token){
        this.id = id;
        this.name = name;
        this.email = email;
        this.cookie_key = token;
        this.offers = new ArrayList<>();
        this.reviews = new ArrayList<>();

    }

    public String dump_info(){
        String info = "###########\n" + "id: " + id + "\n" + "name: " + name + "\n" + "email: " + email + "\n" + cookie_key + "\n" + "###########" ;
        System.out.println(info);
        return info;

    }

    public String toString(){
        return dump_info();
    }
    public void addToOffers(Offer offer) {
        offers.add(offer);
    }
    public void addToReviews(Review review) {
        reviews.add(review);
    }
    public ArrayList<Review> getAllReviews()
    {
        return reviews;
    }
}