package com.barterbayucsb.barterbay;

import android.support.design.widget.Snackbar;
import android.view.View;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamClass;

import static android.os.Environment.getExternalStorageDirectory;

/**
 * Created by Daniel on 2/20/2017.
 */

class SerializableSettings implements java.io.Serializable{
    private boolean FILTER_BY_AGE = false,FILTER_BY_LOCATION = false,FILTER_BY_PRICE = false,FILTER_LOW_TO_HIGH = false;
    private int DISTANCE = 0;


    private String PhoneNo = "(123) 456-7890";

    private final long serialVersionUID = ObjectStreamClass.lookup(this.getClass()).getSerialVersionUID();

    public SerializableSettings(int DISTANCE, boolean FILTER_BY_AGE, boolean FILTER_BY_LOCATION, boolean FILTER_BY_PRICE, boolean FILTER_LOW_TO_HIGH, String PhoneNo) {
        this.DISTANCE = DISTANCE;
        this.FILTER_BY_AGE = FILTER_BY_AGE;
        this.FILTER_BY_LOCATION = FILTER_BY_LOCATION;
        this.FILTER_BY_PRICE = FILTER_BY_PRICE;
        this.FILTER_LOW_TO_HIGH = FILTER_LOW_TO_HIGH;
        this.PhoneNo = PhoneNo;
    }

    public SerializableSettings() {
    }
    public String getPhoneNo() {
        return PhoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        PhoneNo = phoneNo;
    }

    public boolean getFILTER_LOW_TO_HIGH() {return FILTER_LOW_TO_HIGH;}
    public boolean getFILTER_BY_AGE(){
        return FILTER_BY_AGE;
    }
    public boolean getFILTER_BY_LOCATION(){
        return FILTER_BY_LOCATION;
    }
    public boolean getFILTER_BY_PRICE(){
        return FILTER_BY_PRICE;
    }
    public void setFILTER_BY_AGE(boolean b)
    {
        FILTER_BY_AGE = b;
    }
    public void setFILTER_BY_LOCATION(boolean b)
    {
        FILTER_BY_LOCATION = b;
    }
    public void setFILTER_BY_PRICE(boolean b)
    {
        FILTER_BY_PRICE = b;
    }
    public void setFILTER_LOW_TO_HIGH(boolean b){FILTER_LOW_TO_HIGH = b;}
    public void setDISTANCE(int i)
    {
        DISTANCE = i;
    }
    public int getDISTANCE() {
        return DISTANCE;
    }
    public float getDISTANCEfloat()
    {
        return ((float)DISTANCE);
    }

    public static String getPath(){
        return (getExternalStorageDirectory().toString() + "/settings/default.config");

    }

    public void writeSettings(View view) throws IOException {   //now working!
        String path = getPath();
        File myFile = new File(path);
        myFile.getParentFile().mkdirs();                                //makes the directory for file with path obtained from getPath
        Boolean created = myFile.createNewFile();       //if the file doesn't already exist it makes the file, stores if it created a file in created (not yet being used by anything)

        FileOutputStream out = new FileOutputStream(myFile.toString());
        //FileOutputStream out = c.openFileOutput(getPath(), Context.MODE_PRIVATE);
        ObjectOutputStream outStream = new ObjectOutputStream(out);
        outStream.writeObject(this);
        outStream.close();
        out.close();
        Snackbar.make(view, "Wrote settings to "+getPath(), Snackbar.LENGTH_SHORT).show();

    }
    public static SerializableSettings readSettings(File f) throws IOException, ClassNotFoundException {
        FileInputStream fileIn = new FileInputStream(f.getPath());
        ObjectInputStream in = new ObjectInputStream(fileIn);
        SerializableSettings ss = (SerializableSettings) in.readObject();


        in.close();
        fileIn.close();



        return ss;
    }

}
