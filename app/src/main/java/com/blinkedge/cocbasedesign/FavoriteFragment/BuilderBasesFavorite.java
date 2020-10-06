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

import com.blinkedge.cocbasedesign.Activities.BuilderBasesActivity;
import com.blinkedge.cocbasedesign.Activities.HomeVillageBasesActivity;
import com.blinkedge.cocbasedesign.Modal.Modal;
import com.blinkedge.cocbasedesign.R;
import com.blinkedge.cocbasedesign.RecyclerViewAdapter.BuilderFavoriteBasesRecyclerView;
import com.blinkedge.cocbasedesign.RecyclerViewAdapter.VillageFavoriteBasseRecyclerView;
import com.cooltechworks.views.shimmer.ShimmerRecyclerView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class BuilderBasesFavorite extends Fragment {
    private View view;
    private ShimmerRecyclerView builderFavoriteRecycler;
    private LinearLayout builderFavoriteNoImage;
    private Gson gson;
    List<Modal> favList;
    private SharedPreferences sharedPreferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_builder_bases_favorite, container, false);
        favList = new ArrayList<>();
        gson = new Gson();
        sharedPreferences = getActivity().getSharedPreferences("COCDATA", Context.MODE_PRIVATE);
        id();
        loadData();

        return view;
    }

    private void loadData() {
        String s = sharedPreferences.getString("builderfav","");
        if (s != null)
            if (!s.equals(""))
                BuilderBasesActivity.builderFavoriteModalList = gson.fromJson(s, new TypeToken<List<Modal>>() {}.getType());

        Log.d("sizeFavse__", BuilderBasesActivity.builderFavoriteModalList+"favouri=size");
        BuilderFavoriteBasesRecyclerView builderFavoriteBasesRecyclerView  = new BuilderFavoriteBasesRecyclerView(getActivity(),
                BuilderBasesActivity.builderFavoriteModalList);
        builderFavoriteRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        builderFavoriteRecycler.setAdapter(builderFavoriteBasesRecyclerView);
    }


    private void id() {
        builderFavoriteRecycler = view.findViewById(R.id.builderFavoriteRecycler);
        builderFavoriteNoImage = view.findViewById(R.id.builderFavoriteNoImage);
    }
}