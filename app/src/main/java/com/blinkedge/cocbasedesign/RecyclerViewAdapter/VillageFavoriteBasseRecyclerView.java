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

import com.blinkedge.cocbasedesign.Modal.Modal;
import com.blinkedge.cocbasedesign.R;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.util.List;

public class VillageFavoriteBasseRecyclerView extends RecyclerView.Adapter<favoriteBase> {

    private Context context;
    private List<Modal> modals;
    private View view;
    private Gson gson;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public VillageFavoriteBasseRecyclerView(Context context, List<Modal> modal) {
        this.context = context;
        this.modals = modal;
        gson = new Gson();
        sharedPreferences = context.getSharedPreferences("COCDATA", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    @NonNull
    @Override
    public favoriteBase onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_village_favorite_view, parent, false);

        return new favoriteBase(view);
    }

    @Override
    public void onBindViewHolder(@NonNull favoriteBase holder, final int position) {

        Glide.with(context).load(modals.get(position).getVillageAllBases()).centerCrop().placeholder(R.drawable.loading)
                .into(holder.villageBasesImage);

        holder.villageCopyBase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("asfde__","=_="+modals.get(position).getvillageBaseLink());
                Toast.makeText(context, "link", Toast.LENGTH_SHORT).show();
//                Intent i = new Intent(Intent.ACTION_VIEW , Uri.parse(modals.get(position).getvillageBaseLink()));
//                context.startActivity(i);
            }
        });

        holder.villageUnFavoriteImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                modals.remove(position);
                String s = gson.toJson(modals);
                editor.putString("fav", s).apply();
                notifyDataSetChanged();
            }
        });


    }

    @Override
    public int getItemCount() {
        return modals.size();
    }
}

class favoriteBase extends RecyclerView.ViewHolder {

    ImageView villageBasesImage;
    ImageView villageCopyBase;
    ImageView villageUnFavoriteImage;

    public favoriteBase(@NonNull View itemView) {
        super(itemView);

        villageBasesImage = itemView.findViewById(R.id.villageBasesImageFav);
        villageUnFavoriteImage = itemView.findViewById(R.id.villageUnFavoriteImage);
        villageCopyBase = itemView.findViewById(R.id.villageCopyBase);


    }
}