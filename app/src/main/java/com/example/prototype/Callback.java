package com.example.prototype;

import androidx.recyclerview.widget.DiffUtil;

import java.util.List;

public class Callback extends DiffUtil.Callback{
    private List<Card> oldlist;
    private List<Card> newlist;
    public Callback(List<Card> o,List<Card> n){
        oldlist = o;
        newlist = n;
    }
    @Override
    public int getOldListSize() {
        return oldlist.size();
    }

    @Override
    public int getNewListSize() {
        return newlist.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldlist.get(oldItemPosition).getPicture()==newlist.get(newItemPosition).getPicture();
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return oldlist.get(oldItemPosition)==newlist.get(newItemPosition);
    }
}
