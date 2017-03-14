package com.barterbayucsb.barterbay;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.view.View;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.ArrayList;

import static com.barterbayucsb.barterbay.DispLocalOfferActivity.newArray;

/**
 * Created by Ming Chen on 2/7/2017.
 */

//class Offer implements java.io.Serializable{
class Offer { // since bitmap can't be serialized, we need a helper class for saving a post to a file.
    private String name = "";
    private String description = "";
    private String user_id;
    private String picture_url;
    private int value;
    private String path = "";
    private double latitude;
    private double longitude;
    private static double DEFAULT_LATITUDE = 34.4140;
    private static double DEFAULT_LONGITUTDE = -119.8489;
    static public int TOTAL_OFFER_NUM = 10;
    private String updated_at = null;
    private String created_at = null;
    private String TEST_UPDATE_TIME = "2017-03-12T23:30:19.214Z";
    Bitmap image;
    Bitmap DEFAULT_BITMAP = Bitmap.createBitmap(newArray, 2, 2, Bitmap.Config.ALPHA_8);
    public static int PICTURE_SIZE = 512;
    String id;


    public Offer() {
        int[] newArray = new int[]{0xffffffff, 0x00000000, 0x00000000, 0xffffffff};

        name = "test title";
        id = "test id";
        description = "a test item";
        value = 1;
        latitude = 34.4140;
        longitude = -119.8489;
        updated_at = TEST_UPDATE_TIME;

        image = DEFAULT_BITMAP;
    }

    public Offer(String description, String id) {
        this.description = description;
        this.id = id;
        name = "test title";
        value = 1;
        latitude = 34.4140;
        longitude = -119.8489;
        updated_at = TEST_UPDATE_TIME;
        this.image = DEFAULT_BITMAP;
       
    }

    public Offer(String id, String user_id, String content, String picture_url, String updated_at , String created_at, Bitmap offer_pic){
        latitude = DEFAULT_LATITUDE;
        longitude = DEFAULT_LONGITUTDE;
        this.id = id;
        this.user_id = user_id;
        description = content;
        this.picture_url = picture_url;
        this.updated_at = updated_at;
        this.created_at = created_at;


        //this.name = this.description + " by " + mGate.retrieve_user_by_id(user_id).get_name();
        this.name = "default name";
        if (offer_pic == null){
            this.image = DEFAULT_BITMAP;
        }
        else {
            this.image = Bitmap.createScaledBitmap(offer_pic, PICTURE_SIZE, PICTURE_SIZE, false);
        }


    }
    public String get_time_stamp(){
        return this.updated_at;
    }
    public String getPath()
    {
        return path;
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

    public void setValue(int i){
        value = i;
    }



    public void setName(String s) {
        name = s;
    }

    public void setDescription(String s) {
        description = s;
    }
    public void writeOffer(View view) throws IOException {
        SerializableOffer SO = new SerializableOffer(this);
        SO.writeOffer(view);
    }

    public void setLocation(LatLng ll)
    {
        latitude = ll.latitude;
        longitude = ll.longitude;

    }
    public LatLng getLocation()
    {
        return new LatLng(latitude,longitude);
    }

    public String toString(){
        String res = "\nid=" + id + "\nuser_id=" + user_id ;
        res += "\n pic_url=" + picture_url;
        res += "\n updated at=" + updated_at;
        res += "\n########\n";
        return res;
    }
   /* public void writeOffer(View view, Context c) throws IOException {   //This class doesn't write anymore since bitmaps aren't serializable.
        String path = getPath();
        File myFile = new File(path);
        myFile.getParentFile().mkdirs();                                //makes the directory for file with path obtained from getPath
        Boolean created = myFile.createNewFile();       //if the file doesn't already exist it makes the file, stores if it created a file in created (not yet being used by anything)
                                                        //this line always causes a java.io.IOException: No such file or directory

        FileOutputStream out = new FileOutputStream(myFile.toString());
        //FileOutputStream out = c.openFileOutput(getPath(), Context.MODE_PRIVATE);
        ObjectOutputStream outStream = new ObjectOutputStream(out);
        outStream.writeObject(this);
        outStream.close();
        out.close();
        Snackbar.make(view, "Wrote to "+getPath(), Snackbar.LENGTH_SHORT).show();

    }*/
}
