package com.blinkedge.cocbasedesign.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blinkedge.cocbasedesign.R;
import com.blinkedge.cocbasedesign.TabViewAdapter.ThTabViewAdapter;
import com.google.android.material.tabs.TabLayout;

public class HomeFragment extends Fragment {

    TabLayout tabLayout;
    ViewPager viewPager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        id(view);

        assert getFragmentManager() != null;
        ThTabViewAdapter TabViewAdapter = new ThTabViewAdapter(getFragmentManager());
        viewPager.setAdapter(TabViewAdapter);
        viewPager.setCurrentItem(0);
        tabLayout.setupWithViewPager(viewPager);

        return view;
    }

    private void id(View view) {
        tabLayout = view.findViewById(R.id.tab_layout);
        viewPager = view.findViewById(R.id.view_pager);
    }
}