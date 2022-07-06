package com.example.dailyart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.dailyart.AchievementRecyclerView.AchievementCardAdapter;
import com.example.dailyart.AchievementRecyclerView.Achivement;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AchievementsActivity extends AppCompatActivity {

    private DatabaseReference dbRef;

    private AchievementCardAdapter achievementAdapter;
    private RecyclerView recyclerViewAchievement;
    private List<Achivement> achievementsList = new ArrayList<>();

    private boolean review;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achievements);

        //dbRef = FirebaseDatabase.getInstance().getReferenceFromUrl("https://dailyart-a8682-default-rtdb.firebaseio.com/");
        dbRef = FirebaseDatabase.getInstance().getReferenceFromUrl("https://dailyart-mobile-default-rtdb.firebaseio.com/");


        if (achievementsList != null) {
            achievementsList.clear();
        }
        recyclerViewAchievement = findViewById(R.id.recyclerViewAchievement);
        recyclerViewAchievement.setLayoutManager(new LinearLayoutManager(this , LinearLayoutManager.VERTICAL, false));
        recyclerViewAchievement.setHasFixedSize(true);
        achievementAdapter = new AchievementCardAdapter(achievementsList, this);
        recyclerViewAchievement.setAdapter(achievementAdapter);

        dbRef.child("Achievement").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Achivement achievement = dataSnapshot.getValue(Achivement.class);

                    if(achievement.getName().equals("FavouriteArtwork")){
                        dbRef.child("FavouriteArtwork").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot2) {
                                for (DataSnapshot dataSnapshot2 : snapshot2.getChildren()) {
                                    if(dataSnapshot2.getKey().equals(LoginActivity.loggedUser)){
                                        achievementsList.add(achievement);
                                    }
                                }
                                achievementAdapter.notifyDataSetChanged();
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {}
                        });
                    }

                    if(achievement.getName().equals("VisitedArtwork")){
                        dbRef.child("VisitedArtwork").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot2) {
                                for (DataSnapshot dataSnapshot2 : snapshot2.getChildren()) {
                                    if(dataSnapshot2.getKey().equals(LoginActivity.loggedUser)){
                                        achievementsList.add(achievement);
                                    }
                                }
                                achievementAdapter.notifyDataSetChanged();
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {}
                        });
                    }

                    if(achievement.getName().equals("FavouriteMuseum")){
                        dbRef.child("FavouriteMuseum").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot2) {
                                for (DataSnapshot dataSnapshot2 : snapshot2.getChildren()) {
                                    if(dataSnapshot2.getKey().equals(LoginActivity.loggedUser)){
                                        achievementsList.add(achievement);
                                    }
                                }
                                achievementAdapter.notifyDataSetChanged();
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {}
                        });
                    }

                    if(achievement.getName().equals("VisitedMuseum")){
                        dbRef.child("VisitedMuseum").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot2) {
                                for (DataSnapshot dataSnapshot2 : snapshot2.getChildren()) {
                                    if(dataSnapshot2.getKey().equals(LoginActivity.loggedUser)){
                                        achievementsList.add(achievement);
                                    }
                                }
                                achievementAdapter.notifyDataSetChanged();
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {}
                        });
                    }


                    if(achievement.getName().equals("FirstReview")){
                        if(!review){
                            dbRef.child("ReviewMuseum").addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot2) {
                                    for (DataSnapshot dataSnapshot2 : snapshot2.getChildren()) {
                                        if(dataSnapshot2.hasChild(LoginActivity.loggedUser) && !review){
                                            achievementsList.add(achievement);
                                            review = true;
                                        }
                                    }
                                    achievementAdapter.notifyDataSetChanged();
                                }
                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {}
                            });
                        }
                        if(!review){
                            dbRef.child("ReviewArtwork").addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot2) {
                                    for (DataSnapshot dataSnapshot2 : snapshot2.getChildren()) {
                                        if(dataSnapshot2.hasChild(LoginActivity.loggedUser) && !review){
                                            achievementsList.add(achievement);
                                            review = true;
                                        }
                                    }
                                    achievementAdapter.notifyDataSetChanged();
                                }
                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {}
                            });
                        }
                    }



                }
                achievementAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });

    }


}