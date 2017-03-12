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
    private boolean FILTER_BY_AGE = false,FILTER_BY_LOCATION = false,FILTER_BY_PRICE = false;
    private int DISTANCE = 0;

    private final long serialVersionUID = ObjectStreamClass.lookup(this.getClass()).getSerialVersionUID();

    public SerializableSettings(int DISTANCE, boolean FILTER_BY_AGE, boolean FILTER_BY_LOCATION, boolean FILTER_BY_PRICE) {
        this.DISTANCE = DISTANCE;
        this.FILTER_BY_AGE = FILTER_BY_AGE;
        this.FILTER_BY_LOCATION = FILTER_BY_LOCATION;
        this.FILTER_BY_PRICE = FILTER_BY_PRICE;
    }

    public SerializableSettings() {
    }
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
    public void setDISTANCE(int i)
    {
        DISTANCE = i;
    }
    public int getDISTANCE() {
        return DISTANCE;
    }
    public float getDISTANCEfloat()
    {
        return ((float)DISTANCE/100);
    }

    public static String getPath(){
        //return (getExternalStorageDirectory().toString().substring(0,getExternalStorageDirectory().toString().length()-1)+"offers/"+id+".offer");  //there's probably a better way to do this
        return (getExternalStorageDirectory().toString() + "/settings/default.config");

    }

    public void writeSettings(View view) throws IOException {   //now working!
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
        Snackbar.make(view, "Wrote settings to "+getPath(), Snackbar.LENGTH_SHORT).show();

    }
    public static SerializableSettings readOffer(File f) throws IOException, ClassNotFoundException {
        FileInputStream fileIn = new FileInputStream(f.getPath());
        ObjectInputStream in = new ObjectInputStream(fileIn);
        SerializableSettings ss = (SerializableSettings) in.readObject();


        in.close();
        fileIn.close();



        return ss;
    }

}
