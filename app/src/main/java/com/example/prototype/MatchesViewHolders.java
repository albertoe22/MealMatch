package com.example.prototype;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


public class MatchesViewHolders extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView mMatchAddress, mMatchName;
    public ImageView mMatchImage;
    public String placeId;
    public double lat, lon;
    public MatchesViewHolders (View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
        mMatchAddress = (TextView) itemView.findViewById(R.id.MatchAddress);
        mMatchName = (TextView) itemView.findViewById(R.id.MatchName);
        mMatchImage = (ImageView) itemView.findViewById(R.id.MatchImage);
    }
    @Override
    public void onClick(View v) {
        String url = ( "https://www.google.com/maps/search/?api=1&query=" + lat + "," + lon + "&query_place_id=" + placeId );
        Uri uri = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        v.getContext().startActivity(intent);
    }
}
