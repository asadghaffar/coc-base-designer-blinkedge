package com.blinkedge.cocbasedesign.RecyclerViewAdapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.blinkedge.cocbasedesign.Modal.Modal;
import com.blinkedge.cocbasedesign.R;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.util.List;

public class BuilderFavoriteBasesRecyclerView extends RecyclerView.Adapter<builderFavoriteBase> {

    private Context context;
    private List<Modal> modals;
    private View view;
    private Gson gson;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public BuilderFavoriteBasesRecyclerView(Context context, List<Modal> modal) {
        this.context = context;
        this.modals = modal;
        gson = new Gson();
        sharedPreferences = context.getSharedPreferences("COCDATA", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    @NonNull
    @Override
    public builderFavoriteBase onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_builder_favorite_view, parent, false);

        return new builderFavoriteBase(view);
    }

    @Override
    public void onBindViewHolder(@NonNull builderFavoriteBase holder, final int position) {

        Glide.with(context).load(modals.get(position).getBuilderBaseImage()).centerCrop().placeholder(R.drawable.loading).
                into(holder.builderBasesFavoriteImage);

        holder.builderUnFavoriteImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                modals.remove(position);
                String s = gson.toJson(modals);
                editor.putString("builderfav", s).apply();
                notifyDataSetChanged();
            }
        });

    }

    @Override
    public int getItemCount() {
        return modals.size();
    }

}

class builderFavoriteBase extends RecyclerView.ViewHolder {

    ImageView builderBasesFavoriteImage;
    ImageView villageCopyBaseViewfav;
    ImageView builderUnFavoriteImageView;

    public builderFavoriteBase(@NonNull View itemView) {
        super(itemView);

        builderBasesFavoriteImage = itemView.findViewById(R.id.builderBasesFavoriteImage);
        builderUnFavoriteImageView = itemView.findViewById(R.id.builderUnFavoriteImageView);
        villageCopyBaseViewfav = itemView.findViewById(R.id.villageCopyBaseViewfav);


    }
}