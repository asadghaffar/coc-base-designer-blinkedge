package com.blinkedge.cocbasedesign.TabViewAdapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.blinkedge.cocbasedesign.VillageBaseLayoutFragment.FarmingFragment;
import com.blinkedge.cocbasedesign.VillageBaseLayoutFragment.HybridFragment;
import com.blinkedge.cocbasedesign.VillageBaseLayoutFragment.TrophyFragment;
import com.blinkedge.cocbasedesign.VillageBaseLayoutFragment.WarFragment;

public class AllBasesTabViewAdapter extends FragmentStatePagerAdapter {
    public AllBasesTabViewAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if (position == 0)
            return new WarFragment();
        else if (position == 1)
            return new FarmingFragment();
        else if (position == 2)
            return new TrophyFragment();
        else if (position ==3)
            return new HybridFragment();

        return null;
    }

    @Override
    public int getCount() {
        return 4;
    }

    public CharSequence getPageTitle(int position){
        if (position == 0)
            return "War";
        else if (position == 1)
            return "Farming";
        else if (position == 2)
            return "Trophy";
        else if (position == 3)
            return "Hybrid";
        else return "";

    }
}
