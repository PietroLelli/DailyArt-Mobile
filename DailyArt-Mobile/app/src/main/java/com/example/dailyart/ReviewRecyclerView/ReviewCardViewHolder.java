package com.example.dailyart.ReviewRecyclerView;

import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dailyart.R;

public class ReviewCardViewHolder extends RecyclerView.ViewHolder{

    public RatingBar ratingBar;
    public TextView tvUsername;
    public TextView tvDescription;

    public ReviewCardViewHolder(@NonNull View itemView) {
        super(itemView);
        ratingBar = itemView.findViewById(R.id.ratingBarReviewMuseum);
        tvUsername = itemView.findViewById(R.id.tvAchievementTitle);
        tvDescription = itemView.findViewById(R.id.tvAchievementDescription);
    }
}
