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
        ImageView image = (ImageView) convertView.findViewById(R.id.imageView5);

        name.setText(card.getName());

        Glide.with(getContext()).load(card.getImageUrl()).into(image);

        return convertView;

    }
}

