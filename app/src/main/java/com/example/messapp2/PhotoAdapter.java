package com.example.messapp2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.PhotoViewHolder> {

    private List<String> photoList;
    private OnItemClickListener itemClickListener;

    public interface OnItemClickListener {
        void onItemClick(String photoUrl);
    }

    public PhotoAdapter(List<String> photoList, OnItemClickListener itemClickListener) {
        this.photoList = photoList;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public PhotoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_photo, parent, false);
        return new PhotoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoViewHolder holder, int position) {
        // Load and display the image using a library like Glide or Picasso
        // For simplicity, I'll just set the image resource directly
        int resourceId = holder.itemView.getContext().getResources().getIdentifier(photoList.get(position), "drawable", holder.itemView.getContext().getPackageName());
        holder.photoImageView.setImageResource(resourceId);

        holder.itemView.setOnClickListener(v -> {
            if (itemClickListener != null) {
                itemClickListener.onItemClick(photoList.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return photoList.size();
    }

    public class PhotoViewHolder extends RecyclerView.ViewHolder {

        ImageView photoImageView;

        public PhotoViewHolder(@NonNull View itemView) {
            super(itemView);
            photoImageView = itemView.findViewById(R.id.photoImageView);
        }
    }
}

