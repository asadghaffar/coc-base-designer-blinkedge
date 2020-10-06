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
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.blinkedge.cocbasedesign.Activities.BuilderBasesActivity;
import com.blinkedge.cocbasedesign.Modal.Modal;
import com.blinkedge.cocbasedesign.R;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class BuilderAllBasesRecyclerView extends RecyclerView.Adapter<builderBases> {

    private Context context;
    private List<Modal> modalBuilderBases;
    private View view;
    private Gson gson;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public BuilderAllBasesRecyclerView(Context context1, List<Modal> modalList1) {
        this.context = context1;
        this.modalBuilderBases = modalList1;

        gson = new Gson();
        sharedPreferences = context.getSharedPreferences("COCDATA", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        try {
            String list = sharedPreferences.getString("builderfav", "");
            if (list != null) {
                if (!list.equals("")) {
                    Log.d("listChecker_", "=checker");
                    BuilderBasesActivity.builderFavoriteModalList = gson.fromJson(list, new TypeToken<List<Modal>>() {
                    }.getType());
                    if (BuilderBasesActivity.builderFavoriteModalList != null) {
                        if (!BuilderBasesActivity.builderFavoriteModalList.isEmpty()) {
                            for (int indexOuter = 0; indexOuter < modalBuilderBases.size(); indexOuter++) {
                                boolean check = false;
                                for (int index = 0; index < BuilderBasesActivity.builderFavoriteModalList.size(); index++) {
                                    if (BuilderBasesActivity.builderFavoriteModalList.get(index).getBuilderBasesItemId() ==
                                                                    (modalBuilderBases.get(indexOuter).getBuilderBasesItemId()))
                                        check = true;
                                }
                                modalBuilderBases.get(indexOuter).setFavouriteItems(check);
                            }
                        }
                    } else {
                        BuilderBasesActivity.builderFavoriteModalList = new ArrayList<>();
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
    public builderBases onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_builder_all_bases,
                parent, false);

        return new builderBases(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final builderBases holder, final int position) {

        if (modalBuilderBases.get(position).isFavouriteItems()) {
            holder.builderUnFavoriteImage.setVisibility(View.VISIBLE);
            holder.builderFavoriteImage.setVisibility(View.GONE);
        } else {
            holder.builderUnFavoriteImage.setVisibility(View.GONE);
            holder.builderFavoriteImage.setVisibility(View.VISIBLE);
        }

        Glide.with(context).load(modalBuilderBases.get(position).getBuilderBaseImage()).centerCrop().placeholder(R.drawable.loading)
                .into(holder.builderBasesImage);

        holder.builderCopyBase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_VIEW , Uri.parse(modalBuilderBases.get(position).getBuilderBaseLink()));
                context.startActivity(i);
            }
        });

        holder.builderFavoriteImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.builderFavoriteImage.setVisibility(View.INVISIBLE);
                holder.builderUnFavoriteImage.setVisibility(View.VISIBLE);
                BuilderBasesActivity.builderFavoriteModalList.add(modalBuilderBases.get(position));
                save();

            }
        });

        holder.builderUnFavoriteImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.builderUnFavoriteImage.setVisibility(View.INVISIBLE);
                holder.builderFavoriteImage.setVisibility(View.VISIBLE);
                delete(position);


            }
        });

    }

    private void delete(int position) {
        String list = sharedPreferences.getString("builderfav", "");
        if (list != null)
            BuilderBasesActivity.builderFavoriteModalList = gson.fromJson(list, new TypeToken<List<Modal>>() {
            }.getType());
        if (!list.equals("")) {
            for (int i = 0; i < BuilderBasesActivity.builderFavoriteModalList.size(); i++) {
                if (BuilderBasesActivity.builderFavoriteModalList.get(i).getBuilderBasesItemId() ==
                                                                    modalBuilderBases.get(position).getBuilderBasesItemId()) {
                    BuilderBasesActivity.builderFavoriteModalList.remove(i);
                    Toast.makeText(context, "item removed from favourite successfully!", Toast.LENGTH_SHORT).show();
                    String s = gson.toJson(BuilderBasesActivity.builderFavoriteModalList);
                    editor.putString("builderfav", s).apply();
                }
            }

        }

    }

    private void save() {
        String save = gson.toJson(BuilderBasesActivity.builderFavoriteModalList);
        editor.putString("builderfav", save).apply();
        Toast.makeText(context, "item favourite successfully!", Toast.LENGTH_SHORT).show();

    }

    @Override
    public int getItemCount() {
        return modalBuilderBases.size();
    }
}

class builderBases extends RecyclerView.ViewHolder {

    LinearLayout builderBasesFullViewLayout;
    ImageView builderBasesImage;
    ImageView builderFavoriteImage;
    ImageView builderCopyBase;
    ImageView builderUnFavoriteImage;

    public builderBases(@NonNull View itemView) {
        super(itemView);

        builderBasesFullViewLayout = itemView.findViewById(R.id.builderBasesFullViewLayout);
        builderBasesImage = itemView.findViewById(R.id.builderBasesImage);
        builderFavoriteImage = itemView.findViewById(R.id.builderFavoriteImage);
        builderUnFavoriteImage = itemView.findViewById(R.id.builderUnFavoriteImage);
        builderCopyBase = itemView.findViewById(R.id.builderCopyBase);

    }
}