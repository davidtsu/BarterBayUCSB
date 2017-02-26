package com.barterbayucsb.barterbay;

/**
 * Created by Ming Chen on 2/7/2017.
 */

class ServerGate {
    /*
    A class for handling communication with servers
     */
    User test_user= new User();
    Offer test_offer = new Offer();

    //define server result code here
    private static int RESULT_OK = 0;
    User login(String user_id, String user_password) {
        //TODO: implement login. null if login failed, an User instance if login success
        return test_user;
    }

    /*
    retrieve user is a function to get user information from server.
    it sends an http request to user
     */
    User retrieve_user(String  user_id){
        //todo: implement this function
        return test_user;
    }

    /*

     */
    Offer retrieve_offer(String offer_id){
        return test_offer;
    }

    //todo: add more methods here for interacting with server if needed
    //todo: params above like offer_id, user_id is not absoluitely needed, you can change it if need

    static int upload_offer(Offer offer) {
        return RESULT_OK;
    }

}
