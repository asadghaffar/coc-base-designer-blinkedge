package com.blinkedge.cocbasedesign.TabViewAdapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.blinkedge.cocbasedesign.Fragment.BuilderHallFragment;
import com.blinkedge.cocbasedesign.Fragment.TownHallFragment;

public class ThTabViewAdapter extends FragmentStatePagerAdapter {


    public ThTabViewAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if (position == 0)
            return new TownHallFragment();
        else if (position == 1)
            return new BuilderHallFragment();

        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }

    public CharSequence getPageTitle(int position){
        if (position == 0)
            return "Village Base";
        else if (position == 1)
            return "Builder Base";
        else
        return "";
    }
}
