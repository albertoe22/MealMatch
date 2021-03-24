package com.example.prototype;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

public class arrayAdapter extends ArrayAdapter<cards> {

    Context context;

    public arrayAdapter (Context context, int resourceId, List<cards> items) {
        super(context, resourceId, items);
    }
    public View getView(int position, View convertView, ViewGroup parent) {
        cards card = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.card, parent, false);
        }
        TextView name = (TextView) convertView.findViewById(R.id.name);

        // Todo: Instead of using imageviews use recyclerview!!
        ImageView image1 = (ImageView) convertView.findViewById(R.id.imageView4);

        Glide.with(getContext()).load(card.getImageUrl().get(0)).into(image1);

        ImageView image2 = (ImageView) convertView.findViewById(R.id.imageView5);

        Glide.with(getContext()).load(card.getImageUrl().get(1)).into(image2);

        ImageView image3 = (ImageView) convertView.findViewById(R.id.imageView6);

        Glide.with(getContext()).load(card.getImageUrl().get(2)).into(image3);

        name.setText(card.getName());
        return convertView;

    }
}

