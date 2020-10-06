package com.blinkedge.cocbasedesign.RecyclerViewAdapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.blinkedge.cocbasedesign.Activities.HomeVillageBasesActivity;
import com.blinkedge.cocbasedesign.Modal.Modal;
import com.blinkedge.cocbasedesign.R;
import com.bumptech.glide.Glide;

import java.util.List;

public class VillageHallRecyclerView extends RecyclerView.Adapter<townHall> {

    private Context context;
    private List<Modal> modalVillage;
    private View view;

    public VillageHallRecyclerView(Context context1, List<Modal> modalVillage1) {
        this.context = context1;
        this.modalVillage = modalVillage1;
    }

    @NonNull
    @Override
    public townHall onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_town_hall_view,
                parent, false);

        return new townHall(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final townHall holder, final int position) {
        holder.hallTitle.setText(modalVillage.get(position).getTownHallNumber());
        Glide.with(context).load(modalVillage.get(position).getTownHallImage()).centerCrop().placeholder(R.drawable.loading)
                .into(holder.hallImage);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("townhall_onclick_", "id=" + modalVillage.get(position).getTownHallId());

                Intent intent = new Intent(context.getApplicationContext(), HomeVillageBasesActivity.class);
                intent.putExtra("catHome_id", modalVillage.get(position).getTownHallId());
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return modalVillage.size();
    }
}

class townHall extends RecyclerView.ViewHolder {

    ImageView hallImage;
    TextView hallTitle;

    public townHall(@NonNull View itemView) {
        super(itemView);
        hallImage = itemView.findViewById(R.id.hallImage);
        hallTitle = itemView.findViewById(R.id.hallTitle);
    }
}