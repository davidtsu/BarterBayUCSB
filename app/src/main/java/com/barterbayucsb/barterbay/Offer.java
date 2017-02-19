package com.barterbayucsb.barterbay;

import android.graphics.Bitmap;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import static android.R.attr.bitmap;

/**
 * Created by Ming Chen on 2/7/2017.
 */

class Offer implements java.io.Serializable{
    private String name = "";
    private String description = "";
    private int value;
    Bitmap image;
    String id;


    public Offer() {
        int[] newArray = new int[]{0xffffffff, 0x00000000, 0x00000000, 0xffffffff};

        name = "test user";
        id = "test id";
        description = "a test item";
        value = 1;
        image = Bitmap.createBitmap(newArray, 2, 2, Bitmap.Config.ALPHA_8);
    }

    public int getValue(){
        return value;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getPath(){
        return ("/offers/"+id+".offer");
    }

    public void setValue(int i){
        value = i;
    }


    public void setName(String s) {
        name = s;
    }

    public void setDescription(String s) {
        description = s;
    }
    public void writeOffer() throws IOException {
        FileOutputStream out = new FileOutputStream(getPath());
        ObjectOutputStream outStream = new ObjectOutputStream(out);
        outStream.writeObject(this);
        outStream.close();
        out.close();
    }
}
