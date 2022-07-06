package com.example.dailyart;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    private DatabaseReference dbRef;
    public static String loggedUser = "";

    private TextView register;
    private Button btn;
    private EditText username;
    private EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        dbRef = FirebaseDatabase.getInstance().getReferenceFromUrl("https://dailyart-mobile-default-rtdb.firebaseio.com/");

        username = findViewById(R.id.tvUsername);
        password = findViewById(R.id.tvPassword);

        register = findViewById(R.id.tvRegister);
        register.setOnClickListener(view -> openRegister());

        btn = findViewById(R.id.btnLogin);
        btn.setOnClickListener(view -> login());
    }

    public void openRegister(){
        Intent i = new Intent();
        ComponentName componentName = new ComponentName(getApplicationContext(), RegisterActivity.class);
        i.setComponent(componentName);
        startActivity(i);
    }

    public void login() {
        String userText = username.getText().toString();
        String pswText = password.getText().toString();

        if (userText.isEmpty() || pswText.isEmpty()){
            Toast.makeText(getBaseContext(), "Username o Password non inseriti!", Toast.LENGTH_SHORT).show();
        } else {
            dbRef.child("User").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.hasChild(userText)){
                        final String getPsw = snapshot.child(userText).child("password").getValue(String.class);
                        if (getPsw.equals(pswText)) {
                            loggedUser = userText;
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(getBaseContext(), "Password non corretta!", Toast.LENGTH_SHORT).show();
                            password.setText("");
                        }
                    } else {
                        Toast.makeText(getBaseContext(), "Username non corretto!", Toast.LENGTH_SHORT).show();
                        username.setText("");
                        password.setText("");
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {}
            });
        }
    }
}