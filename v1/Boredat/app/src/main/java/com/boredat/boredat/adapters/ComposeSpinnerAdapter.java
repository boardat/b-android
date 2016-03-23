package com.boredat.boredat.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.boredat.boredat.R;

import java.util.List;

/**
 * Created by Liz on 3/21/2016.
 * Modified from original at: http://www.edureka.co/blog/custom-spinner-in-android/
 */
public class ComposeSpinnerAdapter extends ArrayAdapter {
    private List<String> mObjects;
    private Context mCtx;
    private Integer[] images = {
            R.drawable.profile_picture,
            R.drawable.personality_thumbnail_malloc
    };

    public ComposeSpinnerAdapter(Context context, int textViewResourceId, List<String> objects) {
        super(context, textViewResourceId, objects);
        mCtx = context;
        mObjects = objects;
    }

    public View getCustomView(int position, View convertView,
                              ViewGroup parent) {

        // Inflating the layout for the custom Spinner
        LayoutInflater inflater = (LayoutInflater) mCtx.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        View layout = inflater.inflate(R.layout.personality_line_item_inverted, parent, false);

        // Declaring and Typecasting the textview in the inflated layout
        TextView tv = (TextView) layout
                .findViewById(R.id.personality_line_item_inverted_tv);

        // Setting the text using the ArrayList
        tv.setText(mObjects.get(position));


        // Declaring and Typecasting the imageView in the inflated layout
        ImageView img = (ImageView) layout.findViewById(R.id.personality_line_item_inverted_iv);

        // Setting an image using the id's in the array
        // todo custom image loaded
        img.setImageResource(images[position]);

        return layout;
    }

    public View getCustomDropdownView(int position, View convertView, ViewGroup parent) {
        // Inflating the layout for the custom Spinner
        LayoutInflater inflater = (LayoutInflater) mCtx.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        View layout = inflater.inflate(R.layout.personality_line_item, parent, false);

        // Declaring and Typecasting the textview in the inflated layout
        TextView tv = (TextView) layout
                .findViewById(R.id.personality_line_item_tv);

        // Setting the text using the ArrayList
        tv.setText(mObjects.get(position));


        // Declaring and Typecasting the imageView in the inflated layout
        ImageView img = (ImageView) layout.findViewById(R.id.personality_line_item_iv);

        // Setting an image using the id's in the array
        // todo custom image loaded
        img.setImageResource(images[position]);

        return layout;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getCustomDropdownView(position, convertView, parent);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

}