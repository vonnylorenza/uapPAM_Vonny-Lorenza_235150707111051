package com.example.uap.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.uap.R;
import com.example.uap.model.PlantItem;

import java.util.List;

public class PlantItemAdapter extends RecyclerView.Adapter<PlantItemAdapter.ViewHolder> {

    private Context context;
    private List<PlantItem> plantItemList;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onDeleteClick(String itemId);
        void onDetailClick(PlantItem item);
    }

    public PlantItemAdapter(Context context, List<PlantItem> plantItemList, OnItemClickListener listener) {
        this.context = context;
        this.plantItemList = plantItemList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_plant, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PlantItem item = plantItemList.get(position);
        holder.tvPlantName.setText(item.getNamaTanaman());

        holder.tvPlantPrice.setText(String.format("Rp %.0f", item.getHarga()));

        if (item.getImageUrl() != null && !item.getImageUrl().isEmpty()) {
            Glide.with(context)
                    .load(item.getImageUrl())
                    .placeholder(R.drawable.placeholder_plant)
                    .error(R.drawable.placeholder_plant)
                    .into(holder.ivPlantImage);
        } else {
            holder.ivPlantImage.setImageResource(R.drawable.placeholder_plant);
        }

        holder.btnDelete.setOnClickListener(v -> {
            if (listener != null) {
                listener.onDeleteClick(item.getId());
            }
        });

        holder.btnDetail.setOnClickListener(v -> {
            if (listener != null) {
                listener.onDetailClick(item);
            }
        });
    }

    @Override
    public int getItemCount() {
        return plantItemList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivPlantImage;
        TextView tvPlantName, tvPlantPrice;
        Button btnDelete, btnDetail;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivPlantImage = itemView.findViewById(R.id.plantImageView);
            tvPlantName = itemView.findViewById(R.id.plantNameTextView);
            tvPlantPrice = itemView.findViewById(R.id.plantPriceTextView);
            btnDelete = itemView.findViewById(R.id.deleteButton);
            btnDetail = itemView.findViewById(R.id.detailButton);
        }
    }
}
