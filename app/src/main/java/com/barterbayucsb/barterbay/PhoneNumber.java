package com.barterbayucsb.barterbay;

/**
 * Created by David on 3/3/2017.
 */

public class PhoneNumber {
    private int[] phone; //must be 10 digits

    public PhoneNumber(){
        phone = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 0};
    }

    public PhoneNumber(int[] ph){
        phone = ph;
    }

    public String getPhone(){
        return phoneToString();
    }

    public void setPhone(int[] ph){
        phone = ph;
    }

    public String phoneToString() {
        return "(" + phone[0] + "" + phone[1] + phone[2] + ") " + phone[3] + phone[4] + phone[5] + " - " + phone[6] + phone[7] + phone[8] + phone[9];
    }
}
