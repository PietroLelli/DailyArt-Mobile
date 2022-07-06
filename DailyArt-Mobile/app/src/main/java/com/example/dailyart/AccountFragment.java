package com.example.dailyart;

import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;

public class AccountFragment extends Fragment {

    private FirebaseStorage storage;
    private StorageReference storageReference;

    private FloatingActionButton fab;
    private ImageView imgProfilePhoto;
    private TextView username;
    private TextView userSettings;
    private ImageView imageSettings;
    private FragmentAdapter adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);

        TabLayout tabLayout = view.findViewById(R.id.tabLayout);
        ViewPager2 pager2 = view.findViewById(R.id.viewPager);
        FragmentFavourites toVisit = new FragmentFavourites();
        FragmentVisited visited = new FragmentVisited();

        fab = view.findViewById(R.id.fab_achievement);

        storage = FirebaseStorage.getInstance();

        imgProfilePhoto = view.findViewById(R.id.imgProfilePhoto);
        try {
            File file = File.createTempFile(LoginActivity.loggedUser, "jpeg");
            storageReference = storage.getReference().child("image/" + LoginActivity.loggedUser + ".jpeg");

            storageReference.getFile(file).addOnSuccessListener(taskSnapshot -> {
                Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                imgProfilePhoto.setImageBitmap(bitmap);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        fab.setOnClickListener(view1 -> {
            Intent i = new Intent();
            ComponentName componentName = new ComponentName(getContext(), AchievementsActivity.class);
            i.setComponent(componentName);
            startActivity(i);

        });

        username = view.findViewById(R.id.tv_username_account);
        username.setText("Benvenuto/a \n" + LoginActivity.loggedUser + "!");

        userSettings = view.findViewById(R.id.tvUserSettings);
        userSettings.setOnClickListener(v -> openUserSettings());

        imageSettings = view.findViewById(R.id.imageSettings);
        imageSettings.setOnClickListener(v2 -> openAppSettings());

        adapter = new FragmentAdapter(getChildFragmentManager(), getLifecycle(), tabLayout.getTabCount(), toVisit, visited);
        pager2.setAdapter(adapter);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                pager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                pager2.setCurrentItem(tab.getPosition());
            }
        });

        pager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
                @Override
                public void onPageSelected(int position) {
                    tabLayout.selectTab(tabLayout.getTabAt(position));
                }
        });
        return view;


    }

    public void openUserSettings() {
        Intent i = new Intent();
        ComponentName componentName = new ComponentName(getContext(), UserSettingsActivity.class);
        i.setComponent(componentName);
        startActivity(i);
    }

    public void openAppSettings() {
        Intent i = new Intent();
        ComponentName componentName = new ComponentName(getContext(), AppSettingsActivity.class);
        i.setComponent(componentName);
        startActivity(i);
    }
}