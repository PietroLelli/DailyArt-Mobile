package com.example.dailyart;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class AppSettingsActivity extends AppCompatActivity {

    private DatabaseReference dbRef;
    private FirebaseStorage storage;

    private TextView logout;
    private TextView tvDeleteAccount;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_settings);

        logout = findViewById(R.id.tvLogout);
        logout.setOnClickListener(view -> logout());

        tvDeleteAccount = findViewById(R.id.tvDeleteAccount);
        tvDeleteAccount.setOnClickListener(view -> deleteAccount());
    }

    public void logout() {
        Intent intent = new Intent(AppSettingsActivity.this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    public void deleteAccount() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AlertDialogStyle);
        builder.setMessage("Sei sicuro di voler eliminare l'account?")
                .setPositiveButton("Sì", (dialog, id) -> {
                    dbRef = FirebaseDatabase.getInstance().getReferenceFromUrl("https://dailyart-mobile-default-rtdb.firebaseio.com/");

                    dbRef.child("User").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            dbRef.child("User").child(LoginActivity.loggedUser).removeValue();
                            dbRef.child("FavouriteArtwork").child(LoginActivity.loggedUser).removeValue();
                            dbRef.child("FavouriteMuseum").child(LoginActivity.loggedUser).removeValue();
                            dbRef.child("VisitedArtwork").child(LoginActivity.loggedUser).removeValue();
                            dbRef.child("VisitedMuseum").child(LoginActivity.loggedUser).removeValue();
                            LoginActivity.loggedUser ="";
                            Toast.makeText(getApplicationContext(), "Account eliminato con successo", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {}
                    });

                    storage = FirebaseStorage.getInstance();
                    StorageReference storageRef = storage.getReference().child("image/" + LoginActivity.loggedUser + ".jpeg");
                    storageRef.delete();

                    Intent intent = new Intent(AppSettingsActivity.this, LoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                }).setNegativeButton("No", (dialog, id) -> dialog.cancel());
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
