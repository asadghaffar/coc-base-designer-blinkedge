package com.blinkedge.cocbasedesign.TabViewAdapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.blinkedge.cocbasedesign.FavoriteFragment.BuilderBasesFavorite;
import com.blinkedge.cocbasedesign.FavoriteFragment.TownHallBasesFavorite;

public class FavoriteTabViewAdapter extends FragmentStatePagerAdapter {
    public FavoriteTabViewAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if (position == 0)
            return new TownHallBasesFavorite();
        else if (position == 1)
            return new BuilderBasesFavorite();

        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }

    public CharSequence getPageTitle(int positon) {
        if (positon == 0)
            return "Village Bases";
        else if (positon == 1)
            return "Builder Bases";
        return null;
    }

}
