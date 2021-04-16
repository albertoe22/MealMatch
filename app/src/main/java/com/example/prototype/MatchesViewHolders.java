package com.example.prototype;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


public class MatchesViewHolders extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView mMatchAddress, mMatchName;
    public ImageView mMatchImage;
    public MatchesViewHolders (View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
        mMatchAddress = (TextView) itemView.findViewById(R.id.MatchAddress);
        mMatchName = (TextView) itemView.findViewById(R.id.MatchName);
        mMatchImage = (ImageView) itemView.findViewById(R.id.MatchImage);
    }
    @Override
    public void onClick(View v) {

    }
}
