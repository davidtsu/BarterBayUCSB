package com.barterbayucsb.barterbay;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.design.widget.Snackbar;
import android.view.View;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import static android.os.Environment.getExternalStorageDirectory;

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
        return (getExternalStorageDirectory().toString().substring(0,getExternalStorageDirectory().toString().length()-1)+"offers/"+id+".offer");  //there's probably a better way to do this

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
    public void writeOffer(View view, Context c) throws IOException {   //TODO: figure out why it always gets a java.io.IOException: No such file or directory
        String path = getPath();
        File myFile = new File(path);
        myFile.mkdirs();                                //makes the directory for file with path obtained from getPath
        Boolean created = myFile.createNewFile();       //if the file doesn't already exist it makes the file, stores if it created a file in created (not yet being used by anything)
                                                        //this line always causes a java.io.IOException: No such file or directory

        FileOutputStream out = new FileOutputStream(myFile.toString());
        //FileOutputStream out = c.openFileOutput(getPath(), Context.MODE_PRIVATE);
        ObjectOutputStream outStream = new ObjectOutputStream(out);
        outStream.writeObject(this);
        outStream.close();
        out.close();
        Snackbar.make(view, "Wrote to "+getPath(), Snackbar.LENGTH_SHORT).show();

    }
}
