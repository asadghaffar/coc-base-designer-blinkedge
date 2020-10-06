package com.blinkedge.cocbasedesign.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.blinkedge.cocbasedesign.Modal.Modal;
import com.blinkedge.cocbasedesign.R;
import com.blinkedge.cocbasedesign.TabViewAdapter.AllBasesTabViewAdapter;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class HomeVillageBasesActivity extends AppCompatActivity {

    private ImageView backImage;
    private TabLayout baseTabLayout;
    private ViewPager baseViewPager;
    public static List<Modal> favoriteModalList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_village_bases);

        id();
        onClick();

        baseTabLayout.setTabGravity(TabLayout.GRAVITY_CENTER);
        baseTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);

        AllBasesTabViewAdapter allBasesTabViewAdapter = new AllBasesTabViewAdapter(getSupportFragmentManager(), 0);
        baseViewPager.setAdapter(allBasesTabViewAdapter);
        baseViewPager.setCurrentItem(0);
        baseViewPager.setOffscreenPageLimit(1);
        baseTabLayout.setupWithViewPager(baseViewPager);

    }

    private void onClick() {
        backImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void id() {
        baseTabLayout = findViewById(R.id.baseTabLayout);
        baseViewPager = findViewById(R.id.baseViewPager);
        backImage = findViewById(R.id.backImage);
    }

}