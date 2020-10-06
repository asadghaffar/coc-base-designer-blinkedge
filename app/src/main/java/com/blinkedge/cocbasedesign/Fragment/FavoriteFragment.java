package com.blinkedge.cocbasedesign.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blinkedge.cocbasedesign.R;
import com.blinkedge.cocbasedesign.TabViewAdapter.FavoriteTabViewAdapter;
import com.google.android.material.tabs.TabLayout;

public class FavoriteFragment extends Fragment {

    private View view;
    private TabLayout favoriteTabLayout;
    private ViewPager favoriteViewPager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = LayoutInflater.from(container.getContext()).inflate(R.layout.fragment_favorite, container, false);

        id();

        assert getFragmentManager() != null;
        FavoriteTabViewAdapter favoriteTabViewAdapter = new FavoriteTabViewAdapter(getFragmentManager());
        favoriteViewPager.setAdapter(favoriteTabViewAdapter);
        favoriteViewPager.setCurrentItem(0);
        favoriteTabLayout.setupWithViewPager(favoriteViewPager);

        return view;
    }

    private void id() {

        favoriteViewPager = view.findViewById(R.id.favoriteViewPager);
        favoriteTabLayout = view.findViewById(R.id.favoriteTabLayout);

    }
}