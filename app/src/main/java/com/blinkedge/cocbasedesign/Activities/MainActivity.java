package com.blinkedge.cocbasedesign.Activities;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.blinkedge.cocbasedesign.Fragment.FavoriteFragment;
import com.blinkedge.cocbasedesign.Fragment.HomeFragment;
import com.blinkedge.cocbasedesign.Fragment.MoreFragment;
import com.blinkedge.cocbasedesign.R;

public class MainActivity extends AppCompatActivity {

    public LinearLayout fragmentLinear;
    public LinearLayout home_fragment;
    public LinearLayout favorite_fragment;
    public LinearLayout more_fragment;
    public ImageView home;
    public ImageView heart;
    public ImageView more;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        id();
        onClick();
        homeIconColorChange();

        replaceFragment(R.id.fragment_linear, new HomeFragment());

    }

    private void homeIconColorChange() {
        // When Activity Load
        home.setColorFilter(getResources().getColor(R.color.yellow), PorterDuff.Mode.SRC_IN);
    }

    private void onClick()  {
        home_fragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replaceFragment(R.id.fragment_linear, new HomeFragment());

                home.setColorFilter(getResources().getColor(R.color.yellow), PorterDuff.Mode.SRC_IN);
                heart.setColorFilter(getResources().getColor(android.R.color.black), PorterDuff.Mode.SRC_IN);
                more.setColorFilter(getResources().getColor(android.R.color.black), PorterDuff.Mode.SRC_IN);


            }
        });

        favorite_fragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replaceFragment(R.id.fragment_linear, new FavoriteFragment());
                home.setColorFilter(getResources().getColor(R.color.black), PorterDuff.Mode.SRC_IN);
                heart.setColorFilter(getResources().getColor(R.color.yellow), PorterDuff.Mode.SRC_IN);
                more.setColorFilter(getResources().getColor(android.R.color.black), PorterDuff.Mode.SRC_IN);
            }
        });

        more_fragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replaceFragment(R.id.fragment_linear, new MoreFragment());
                home.setColorFilter(getResources().getColor(R.color.black), PorterDuff.Mode.SRC_IN);
                heart.setColorFilter(getResources().getColor(android.R.color.black), PorterDuff.Mode.SRC_IN);
                more.setColorFilter(getResources().getColor(R.color.yellow), PorterDuff.Mode.SRC_IN);
            }
        });

    }

    private void id() {
        fragmentLinear = findViewById(R.id.fragment_linear);
        home_fragment = findViewById(R.id.home_fragment);
        favorite_fragment = findViewById(R.id.favorite_fragment);
        more_fragment = findViewById(R.id.more_fragment);
        home = findViewById(R.id.home);
        heart = findViewById(R.id.heart);
        more = findViewById(R.id.more);

    }

    public void replaceFragment(int view, Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(view, fragment);
        fragmentTransaction.commit();

    }
}