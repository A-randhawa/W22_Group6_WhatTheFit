package com.example.w22_group6_whatthefit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class GridAdapter extends BaseAdapter {

    Context context;
    String[] recipeName;
    int[] image;

    LayoutInflater inflater;

    public GridAdapter(Context context, String[] flowerName, int[] image) {
        this.context = context;
        this.recipeName = flowerName;
        this.image = image;
    }

    @Override
    public int getCount() {
        return recipeName.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

       if (inflater == null)
           inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

       if (convertView == null){

           convertView = inflater.inflate(R.layout.grid_item,null);

       }

        ImageView imageView = convertView.findViewById(R.id.grid_image);
        TextView textView = convertView.findViewById(R.id.item_name);

        imageView.setImageResource(image[position]);
        textView.setText(recipeName[position]);

        return convertView;
    }
}
