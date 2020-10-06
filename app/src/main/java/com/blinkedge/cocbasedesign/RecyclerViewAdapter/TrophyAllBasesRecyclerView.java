package com.blinkedge.cocbasedesign.RecyclerViewAdapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.blinkedge.cocbasedesign.Activities.HomeVillageBasesActivity;
import com.blinkedge.cocbasedesign.Modal.Modal;
import com.blinkedge.cocbasedesign.R;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class TrophyAllBasesRecyclerView extends RecyclerView.Adapter<trophyAllBases> {

    private final Context context;
    private List<Modal> modalVillageBases;
    private View view;
    private Gson gson;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public TrophyAllBasesRecyclerView(Context context1, List<Modal> modalVillageBases1) {
        this.context = context1;
        this.modalVillageBases = modalVillageBases1;
        Log.d("sdfwer__",modalVillageBases1.size()+"_");
        gson = new Gson();
        sharedPreferences = context.getSharedPreferences("COCDATA", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        try {
            String list = sharedPreferences.getString("fav", "");
            if (list != null) {
                if (!list.equals("")) {
                    Log.d("asfdwe__", "=checker");
                    HomeVillageBasesActivity.favoriteModalList = gson.fromJson(list, new TypeToken<List<Modal>>() {
                    }.getType());
                    if (HomeVillageBasesActivity.favoriteModalList != null) {
                        if (!HomeVillageBasesActivity.favoriteModalList.isEmpty()) {
                            for (int indexOuter = 0; indexOuter < modalVillageBases.size(); indexOuter++) {
                                boolean check = false;
                                for (int index = 0; index < HomeVillageBasesActivity.favoriteModalList.size(); index++) {
                                    if (HomeVillageBasesActivity.favoriteModalList.get(index).getTrophyItemId()
                                                                    == (modalVillageBases.get(indexOuter).getTrophyItemId()))
                                        check = true;
                                }
                                modalVillageBases.get(indexOuter).setTrophyFavoriteItems(check);
                            }
                        }
                    } else {
                        HomeVillageBasesActivity.favoriteModalList = new ArrayList<>();
                    }
                }
            } else {
                Toast.makeText(context, "Favourite items not found!restart the app", Toast.LENGTH_SHORT).show();
            }
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }

    }

    @NonNull
    @Override
    public trophyAllBases onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_village_all_bases,
                parent, false);

        return new trophyAllBases(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final trophyAllBases holder, final int position) {

        if (modalVillageBases.get(position).isTrophyFavoriteItems()) {
            holder.unFavoriteImage.setVisibility(View.VISIBLE);
            holder.favoriteImage.setVisibility(View.GONE);
        } else {
            holder.unFavoriteImage.setVisibility(View.GONE);
            holder.favoriteImage.setVisibility(View.VISIBLE);
        }

        Glide.with(context).load(modalVillageBases.get(position).getVillageAllBases()).centerCrop().placeholder(R.drawable.loading)
                .into(holder.basesImage);

        holder.copyBase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri;
                Intent i = new Intent(Intent.ACTION_VIEW , Uri.parse(modalVillageBases.get(position).getvillageBaseLink()));
                context.startActivity(i);
            }
        });

        holder.favoriteImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.favoriteImage.setVisibility(View.INVISIBLE);
                holder.unFavoriteImage.setVisibility(View.VISIBLE);
                HomeVillageBasesActivity.favoriteModalList.add(modalVillageBases.get(position));
                save();

            }
        });

        holder.unFavoriteImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.unFavoriteImage.setVisibility(View.INVISIBLE);
                holder.favoriteImage.setVisibility(View.VISIBLE);
                delete(position);


            }
        });

    }

    private void delete(int position) {

        String list = sharedPreferences.getString("fav", "");
        if (list != null)
            HomeVillageBasesActivity.favoriteModalList = gson.fromJson(list, new TypeToken<List<Modal>>() {
            }.getType());
        if (!list.equals("")) {
            for (int i = 0; i < HomeVillageBasesActivity.favoriteModalList.size(); i++) {
                if (HomeVillageBasesActivity.favoriteModalList.get(i).getTrophyItemId() == modalVillageBases.get(position).getTrophyItemId()){
                    HomeVillageBasesActivity.favoriteModalList.remove(i);
                    Toast.makeText(context, "item removed from favourite successfully!", Toast.LENGTH_SHORT).show();
                    String s = gson.toJson(HomeVillageBasesActivity.favoriteModalList);
                    editor.putString("fav", s).apply();
                }
            }

        }

    }

    private void save() {
        String save = gson.toJson(HomeVillageBasesActivity.favoriteModalList);
        editor.putString("fav", save).apply();
        Toast.makeText(context, "item favourite successfully!", Toast.LENGTH_SHORT).show();

    }

    @Override
    public int getItemCount() {

        Log.d("listSize", String.valueOf(modalVillageBases.size()));
        return modalVillageBases.size();

    }
}

class trophyAllBases extends RecyclerView.ViewHolder {

    ImageView basesImage;
    ImageView favoriteImage;
    ImageView copyBase;
    ImageView unFavoriteImage;

    public trophyAllBases(@NonNull View itemView) {
        super(itemView);

        basesImage = itemView.findViewById(R.id.basesImage);
        favoriteImage = itemView.findViewById(R.id.favoriteImage);
        unFavoriteImage = itemView.findViewById(R.id.unFavoriteImage);
        copyBase = itemView.findViewById(R.id.copyBase);

    }
}