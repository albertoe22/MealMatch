package com.example.prototype;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.TextView;
import android.widget.Toast;

import com.yuyakaido.android.cardstackview.CardStackLayoutManager;
import com.yuyakaido.android.cardstackview.CardStackListener;
import com.yuyakaido.android.cardstackview.CardStackView;
import com.yuyakaido.android.cardstackview.Direction;
import com.yuyakaido.android.cardstackview.StackFrom;
import com.yuyakaido.android.cardstackview.SwipeableMethod;

import java.util.ArrayList;
import java.util.List;

public class SwipeActivity extends AppCompatActivity {
    private CardStackLayoutManager manager;
    private Stack adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe);
        CardStackView cardStack = findViewById(R.id.restaurantCard);
        manager = new CardStackLayoutManager(this, new CardStackListener() {
            @Override
            public void onCardDragging(Direction direction, float ratio) {
                Log.d("SwipeActivity","onCardDragging: direction=" + direction.name() + " ratio=" + ratio);
            }

            @Override
            public void onCardSwiped(Direction direction) {
                Log.d("SwipeActivity", "onCardSwiped: position=" + manager.getTopPosition() + " direction=" + direction);
                if(direction==Direction.Right){
                    Toast.makeText(SwipeActivity.this, "Right", Toast.LENGTH_SHORT).show();
                }
                if(direction==Direction.Left){
                    Toast.makeText(SwipeActivity.this, "Left", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCardRewound() {
                Log.d("SwipeActivity", "onCardRewound: " + manager.getTopPosition());
            }

            @Override
            public void onCardCanceled() {
                Log.d("SwipeActivity", "onCardCanceled: " + manager.getTopPosition());
            }

            @Override
            public void onCardAppeared(View view, int position) {
                TextView loc = view.findViewById(R.id.location);
                Log.d("SwipeActivity", "onCardAppeared: " + position + ", location: " + loc.getText());
            }

            @Override
            public void onCardDisappeared(View view, int position) {
                TextView loc = view.findViewById(R.id.location);
                Log.d("SwipeActivity", "onCardDisappeared: " + position + ", location: " + loc.getText());
            }
        });
        //swipe details
        manager.setStackFrom(StackFrom.None);
        manager.setVisibleCount(3);
        manager.setTranslationInterval(8.0f);
        manager.setScaleInterval(0.95f);
        manager.setSwipeThreshold(0.3f);
        manager.setMaxDegree(20.0f);
        manager.setDirections(Direction.FREEDOM);
        manager.setCanScrollHorizontal(true);
        manager.setSwipeableMethod(SwipeableMethod.Manual);
        manager.setOverlayInterpolator(new LinearInterpolator());
        adapter = new Stack(addList());
        cardStack.setLayoutManager(manager);
        cardStack.setAdapter(adapter);
        cardStack.setItemAnimator(new DefaultItemAnimator());
    }

    private List<Card> addList() {
        List<Card> cards = new ArrayList<>();
        //test data
        cards.add(new Card(R.drawable.food_image_1,"random location"));
        cards.add(new Card(R.drawable.up_img,"random location"));
        cards.add(new Card(R.drawable.down_img,"random location"));
        return cards;
    }

    public void toMatches(View view) {
        startActivity(new Intent(this, Matches.class));
    }

    public void toSettings(View view) {
        startActivity(new Intent(this, settings.class));
    }
}