package com.example.dailyart.AchievementRecyclerView;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dailyart.R;
import com.example.dailyart.ReviewRecyclerView.Review;
import com.example.dailyart.ReviewRecyclerView.ReviewCardViewHolder;

import java.util.List;

public class AchievementCardAdapter extends RecyclerView.Adapter<AchievementCardViewHolder>{

    private List<Achivement> achivementList;
    private Activity activity;

    public AchievementCardAdapter(List<Achivement> achivementList, Activity activity) {
        this.achivementList = achivementList;
        this.activity = activity;
    }

    @NonNull
    @Override
    public AchievementCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.card_layout_achivement, parent,false);
        return new AchievementCardViewHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull AchievementCardViewHolder holder, int position) {
        Achivement currentCardItem = achivementList.get(position);
        String imagePath = currentCardItem.getImage();
        Drawable drawable = AppCompatResources.getDrawable(activity, activity.getResources()
                .getIdentifier(imagePath, "drawable", activity.getPackageName()));
        holder.image.setImageDrawable(drawable);
        holder.tvTitle.setText(currentCardItem.getTitle());
        holder.tvDescription.setText(currentCardItem.getDescription());
    }

    @Override
    public int getItemCount() {
        return achivementList.size();
    }
}
