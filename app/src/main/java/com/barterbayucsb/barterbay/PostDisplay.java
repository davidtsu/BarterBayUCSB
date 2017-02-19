package com.barterbayucsb.barterbay;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Daniel on 2/18/2017.
 */

public class PostDisplay  {
    private ImageView IV;
    private String name = "";
    private String description = "";
    private TextView nameTV;
    private TextView descriptionTV;
    String id;

    public PostDisplay(Offer O, Context c) {
        IV = new ImageView(c);
        IV.setImageBitmap(O.image);
        name = O.getName();
        description = O.getDescription();
        id = O.id;
        nameTV.setText(name);
        descriptionTV.setText(description);

    }
    void DisplayPost(Context c)
    {



        return;
    }
}
