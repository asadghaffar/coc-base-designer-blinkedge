package com.blinkedge.cocbasedesign.FavoriteFragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.blinkedge.cocbasedesign.Activities.HomeVillageBasesActivity;
import com.blinkedge.cocbasedesign.Modal.Modal;
import com.blinkedge.cocbasedesign.R;
import com.blinkedge.cocbasedesign.RecyclerViewAdapter.VillageFavoriteBasseRecyclerView;
import com.cooltechworks.views.shimmer.ShimmerRecyclerView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;


public class TownHallBasesFavorite extends Fragment {

    private View view;
    private ShimmerRecyclerView favoriteRecycler;
    private LinearLayout favoriteNoImage;
    private Gson gson;
    List<Modal> favList;
    private SharedPreferences sharedPreferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_town_hall_bases_favorite, container, false);
        favList = new ArrayList<>();
        gson = new Gson();
        sharedPreferences = getActivity().getSharedPreferences("COCDATA", Context.MODE_PRIVATE);
        id();
        loadData();

        return view;
    }

    private void loadData() {
        String s = sharedPreferences.getString("fav", "");
        if (s != null)
            if (!s.equals(""))
                HomeVillageBasesActivity.favoriteModalList = gson.fromJson(s, new TypeToken<List<Modal>>() {
                }.getType());

        Log.d("sizeFavse__", HomeVillageBasesActivity.favoriteModalList + "favouri=size");
        VillageFavoriteBasseRecyclerView villageFavoriteBasseRecyclerView = new VillageFavoriteBasseRecyclerView(getActivity(),
                HomeVillageBasesActivity.favoriteModalList);

        favoriteRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));

        favoriteRecycler.setAdapter(villageFavoriteBasseRecyclerView);
    }


    private void id() {
        favoriteRecycler = view.findViewById(R.id.favoriteRecycler);
        favoriteNoImage = view.findViewById(R.id.farmingNoImage);
    }
}