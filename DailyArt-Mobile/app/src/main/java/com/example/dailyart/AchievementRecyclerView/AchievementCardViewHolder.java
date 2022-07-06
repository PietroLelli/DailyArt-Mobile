package com.example.dailyart.AchievementRecyclerView;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dailyart.R;

public class AchievementCardViewHolder extends RecyclerView.ViewHolder {

    public ImageView image;
    public TextView tvTitle;
    public TextView tvDescription;

    public AchievementCardViewHolder(@NonNull View itemView) {
        super(itemView);
        image = itemView.findViewById(R.id.imgAchievement2);
        tvTitle = itemView.findViewById(R.id.tvAchievementTitle);
        tvDescription = itemView.findViewById(R.id.tvAchievementDescription);
    }
}
