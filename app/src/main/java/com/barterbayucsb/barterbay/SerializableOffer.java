package com.barterbayucsb.barterbay;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.design.widget.Snackbar;
import android.view.View;

import java.io.ByteArrayOutputStream;
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

class SerializableOffer implements java.io.Serializable{
    private String name = "";
    private String description = "";
    private int value;
    byte[] image;
    String path = "";
    String id;

    private final long serialVersionUID = ObjectStreamClass.lookup(this.getClass()).getSerialVersionUID();//TODO: fix this



    public SerializableOffer() {
        int[] newArray = new int[]{0xffffffff, 0x00000000, 0x00000000, 0xffffffff};

        name = "test user";
        id = "test id";
        description = "a test item";
        value = 1;
        //image = Bitmap.createBitmap(newArray, 2, 2, Bitmap.Config.ALPHA_8); TODO: potentially make a default byte array of the example bitmap
    }
    public SerializableOffer(Offer offer) {

        name = offer.getName();
        id = offer.id;
        description = offer.getDescription();
        value = offer.getValue();
        path = offer.getPath();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        offer.image.compress(Bitmap.CompressFormat.PNG, 100, stream);
        image = stream.toByteArray();

    }
    public Offer toOffer()
    {
        Offer offer = new Offer();
        offer.setName(name);
        offer.id = id;
        offer.setDescription(description);
        offer.setValue(value);

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inMutable = true;
        Bitmap bmp = BitmapFactory.decodeByteArray(image, 0, image.length, options);
        //Canvas canvas = new Canvas(bmp);


        offer.image = bmp;

        return offer;
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
        //return (getExternalStorageDirectory().toString().substring(0,getExternalStorageDirectory().toString().length()-1)+"offers/"+id+".offer");  //there's probably a better way to do this
        return (getExternalStorageDirectory().toString() + "/offers/"+id+".offer");

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
    public void writeOffer(View view) throws IOException {   //now working!
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

    }
    public static Offer readOffer(File f) throws IOException, ClassNotFoundException {
        FileInputStream fileIn = new FileInputStream(f.getPath());
        ObjectInputStream in = new ObjectInputStream(fileIn);
        SerializableOffer so = (SerializableOffer) in.readObject();


        in.close();
        fileIn.close();

        Offer o = so.toOffer();


        return o;
    }
}
