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

import com.blinkedge.cocbasedesign.Activities.BuilderBasesActivity;
import com.blinkedge.cocbasedesign.Modal.Modal;
import com.blinkedge.cocbasedesign.R;
import com.bumptech.glide.Glide;

import java.util.List;


public class BuilderHallRecyclerView extends RecyclerView.Adapter<builderHall> {

    private Context context;
    private List<Modal> modals;
    private View view;


    public BuilderHallRecyclerView(Context context1, List<Modal> modals1) {
        this.context = context1;
        this.modals = modals1;

    }

    @NonNull
    @Override
    public builderHall onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_builder_hall_view,
                parent, false);

        return new builderHall(view);
    }

    @Override
    public void onBindViewHolder(@NonNull builderHall holder, final int position) {
        holder.builderHallTitle.setText(modals.get(position).getBuilderBaseTitle());
        Glide.with(context).load(modals.get(position).getBuilderBaseImage()).centerCrop().placeholder(R.drawable.loading)
                .into(holder.builderHallImage);

        holder.builderHallImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("builderHallOnclick_", "id=" + modals.get(position).getBuilderHallId());

                Intent intent = new Intent(context.getApplicationContext(), BuilderBasesActivity.class);
                intent.putExtra("catHome_id", modals.get(position).getBuilderHallId());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return modals.size();
    }
}

class builderHall extends RecyclerView.ViewHolder {

    TextView builderHallTitle;
    ImageView builderHallImage;

    public builderHall(@NonNull View itemView) {
        super(itemView);

        builderHallTitle = itemView.findViewById(R.id.builderHallTitle);
        builderHallImage = itemView.findViewById(R.id.builderHallImage);

    }
}